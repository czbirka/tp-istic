package controllers;

import play.libs.F;
import play.libs.ws.WS;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import services.IUsersService;
import services.UsersServiceHTTP;
import services.models.User;

import java.util.Map;

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
    static IUsersService service = new UsersServiceHTTP(WS.client());

    
    public static boolean connectionPossible() {
        return !service.allUsers().get(DEFAULT_TIMEOUT).isEmpty();
    }

    public static F.Promise<Result> users() {
        return service.allUsers()
                .map(users -> ok(views.html.users.render(users)));
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
