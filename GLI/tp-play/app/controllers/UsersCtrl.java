package controllers;

import play.Routes;
import play.data.Form;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;
import play.libs.F;
import play.libs.ws.WS;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import scala.NotImplementedError;
import services.IUsersService;
import services.UsersService;
import services.models.Ride;
import services.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static play.data.Form.form;

/**
 * Controller grouping actions related to the users service.
 */
@Security.Authenticated(Authenticator.class)
public class UsersCtrl extends Controller {

    public final static Long DEFAULT_TIMEOUT = 5000L;

    /**
     * The entry point to the service implementation.
     */
    static IUsersService service = new UsersService(WS.client());


    public static F.Promise<Result> users() {
        return service.allUsers()
                .map(users -> ok(views.html.users.render(users)));
    }

    public static F.Promise<Result> userForm() {
        return F.Promise.promise(() -> ok(views.html.userForm.render(form(User.class))));
    }

    public static F.Promise<Result> createUser() {
        Form<User> form = Form.form(User.class).bindFromRequest();

        if (form.hasErrors()) {
            return F.Promise.promise(() -> badRequest(views.html.userForm.render(form)));
        }

        return service.createUser(form.get())
                .map(b -> redirect(routes.Journeys.rides()));
    }

    public static F.Promise<Result> create(User user) {
        return service.createUser(user)
                .map(b -> redirect(routes.Journeys.rides()));
    }

    public static F.Promise<User> getUserByName(String username) {
        return service.getUserByName(username);
    }

    public static F.Promise<Result> user(Long id) {
        return service.getUserById(id).map(user -> ok(views.html.userView.render(user)));
    }

    public static F.Promise<Map<String, Boolean>> authenticate(User user) {
        return service.authenticate(user);
    }


}
