package controllers;

import play.Routes;
import play.data.Form;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;
import play.libs.F;
import play.libs.ws.WS;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.Security;
import scala.NotImplementedError;
import services.IRidesService;
import services.RidesServiceHTTP;
import services.models.Ride;
import services.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static play.data.Form.form;

/**
 * Controller grouping actions related to the journeys service.
 */
@Security.Authenticated(Authenticator.class)
public class Journeys extends Controller {

    public final static Long DEFAULT_TIMEOUT = 5000L;

    /**
     * The entry point to the service implementation.
     */
    static IRidesService service = new RidesServiceHTTP(WS.client());

    /**
     * Show all visible journeys
     */
    public static F.Promise<Result> rides() {
        if (!UsersCtrl.connectionPossible()) {
            return F.Promise.promise(() -> redirect(routes.Authentication.logout()));
        }
        return service.allRides()
                .map(journeys -> ok(views.html.index.render(Authentication.username(), journeys)));
    }

    /**
     * Show the details of the journey with the given id
     */
    public static F.Promise<Result> journey(Long id) {
        return service.getRideById(id).map(ride -> {
            if (ride == null)
                return notFound(views.html.pageNotFound.render());
            return ok(views.html.rideView.render(ride));
        });
    }

// public static F.Promise<Result> journey(Long id) {
//        return withJourney(id, (journey -> ok(views.html.journey.render(journey, form(Join.class), form(Attend.class)))));
//    }

    public static F.Promise<Result> rideForm() {
        return F.Promise.promise(() -> ok(views.html.rideForm.render(form(Ride.class))));
    }

    public static F.Promise<Result> joinRide(Long rideId, String username) {
        User user = UsersCtrl.getUserByName(username).get(UsersCtrl.DEFAULT_TIMEOUT);
        return service.addPassenger(rideId, user)
                      .map(r -> {
                          Authentication.getCurrentUser();
                          return redirect(routes.Journeys.journey(r.id));
                      });
    }


    /**
     * Attend to the journey with the given id
     */
    public static F.Promise<Result> attend(Long id) {
        throw new NotImplementedError();
    }

    public static F.Promise<Result> createRide() {
        Form<Ride> form = Form.form(Ride.class).bindFromRequest();

        if (form.hasErrors()) {
            return F.Promise.promise(() -> badRequest(views.html.rideForm.render(form)));
        }

        return service.createRide(form.get()).map(b -> redirect(routes.Journeys.rides()));
    }

    /**
     * Attend to a journey by joining a driver already attending
     */
    public static F.Promise<Result> join(Long journeyId) {
        throw new NotImplementedError();
//        Form<Join> joinForm = Form.form(Join.class).bindFromRequest();
//
//        if (joinForm.hasErrors()) {
//            return withJourney(journeyId, (journey -> badRequest(views.html.journey.render(journey, joinForm, form(Attend.class)))));
//        }
//
//        return null;
    }

    /**
     * Show a page displaying journey’s attendee
     */
    public static F.Promise<Result> observe(Long journeyId) {
        throw new NotImplementedError();
        //return withJourney(journeyId, journey -> ok(views.html.observe.render(journeyId)));
    }

    /**
     * Deletes a given Ride.
     * @param id
     * @return
     */
    public static F.Promise<Result> delete(Long id) {
        return deleteAndRefresh(id, r -> {
            service.deleteById(id);
            return redirect(routes.Journeys.rides());
        });
    }

    /**
     * Stream of participation notifications for the journey with the given id
     */
    public static F.Promise<Result> attendees(Long id) {
        throw new NotImplementedError();
    }

    /**
     * JavaScript resource defining a reverse router allowing us to compute URLs of the application from client-side
     */
    public static Result javaScriptRouter() {
        return ok(Routes.javascriptRouter("routes", routes.javascript.Journeys.attendees()));
    }

    /**
     * Form model for attending as a driver
     */
    public static class Attend {
        @Constraints.Required
        public Integer availableSeats;

        public List<ValidationError> validate() {
            List<ValidationError> errors = new ArrayList<ValidationError>();

            if (availableSeats < 1) {
                errors.add(new ValidationError("availableSeats", "Please enter a strictly positive number."));
            }

            return errors.isEmpty() ? null : errors;
        }
    }

    /**
     * Form model for attending by joining a driver
     */
    public static class Join {
        @Constraints.Required
        public Long driverId;

        public List<ValidationError> validate() {
            List<ValidationError> errors = new ArrayList<ValidationError>();

            if (driverId < 1) {
                errors.add(new ValidationError("driverId", "Please enter a strictly positive number."));
            }

            return errors.isEmpty() ? null : errors;
        }
    }

    public static Ride getRideById(Long id) {
        return service.getRideById(id).get(DEFAULT_TIMEOUT);
    }

/*
    static F.Promise<Result> withJourney(Long id, Function<Journey, Result> f) {
        return service.allJourneys().map(journeys -> {
            return journeys.stream().
                filter(journey -> journey.id.equals(id)).
                findFirst().
                map(f::apply).
                orElseGet(Results::notFound);
        });
    }
*/

    private static F.Promise<Result> deleteAndRefresh(Long id, Function<Ride, Result> f) {
        return service.allRides().map(journeys -> {
            return journeys.stream().
                    filter(r -> r.id.equals(id)).
                    findFirst().
                    map(f::apply).
                    orElseGet(Results::notFound);
        });
    }

}
