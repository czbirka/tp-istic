package controllers;

import play.data.Form;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import services.models.User;


import static play.data.Form.form;

/**
 * Controller grouping actions related to authentication
 */
public class Authentication extends Controller {

    /**
     * User currently logged in.
     */
    private static User currentUser;

    /**
     * Show the authentication form
     */
    public static Result login() {
        return ok(views.html.login.render(form(User.class)));
    }

    /**
     * Handle the authentication form submission.
     *
     * If the submitted data is invalid (e.g. the user password is wrong), this action must return a 400 status code
     * and show again the form with its errors.
     *
     * Otherwise, the user must be authenticated (his user id should be stored into his session) and redirected to the index page.
     */
    public static Result authenticate() {

        Form<User> loginForm = Form.form(User.class).bindFromRequest();

        if (loginForm.hasErrors()) {
            return badRequest(views.html.login.render(loginForm));
        }
        else {
            session().clear();
            session().put("username", loginForm.get().username);
            return redirect(routes.Journeys.rides());
        }
    }

    public static Result registration() {
        return ok(views.html.registration.render(form(User.class)));
    }

    public static F.Promise<Result> register() {
        Form<User> registerForm = Form.form(User.class).bindFromRequest();

        if (registerForm.hasErrors()) {
            return F.Promise.promise(() -> badRequest(views.html.registration.render(registerForm)));
        }
        else {
            session().clear();
            session().put("username", registerForm.get().username);
            return UsersCtrl.create(registerForm.get());
        }
    }

    /**
     * Logs out an user (remove his name from his session) and show a good bye message
     */
    public static Result logout() {
        session().clear();
        currentUser = null;
        return redirect(routes.Authentication.login());
        //return ok(views.html.logout.render());
    }

    /**
     * @return The current user name
     */
    public static String username() {
        return session("username");
    }

    /**
     * @return The current user id
     */
    public static User currentUser() {
        if (currentUser == null)
            getCurrentUser();
        return currentUser;
    }

    public static void getCurrentUser() {
        currentUser = UsersCtrl.getUserByName(username()).get(UsersCtrl.DEFAULT_TIMEOUT);
    }
}
