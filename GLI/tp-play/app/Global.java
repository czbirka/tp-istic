import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;

import static play.mvc.Results.*;

public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {
        Logger.info("Application started");
    }

    @Override
    public void onStop(Application app) {
        Logger.info("Application stopped");
    }

    @Override
    public F.Promise<Result> onHandlerNotFound(Http.RequestHeader req) {
        return F.Promise.promise(() -> notFound(views.html.pageNotFound.render()));
    }

}
