package controllers;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import play.mvc.Http;
import play.test.*;

import java.util.Collections;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationTest extends WithBrowser {

    public static FakeApplication app;
    @Mock
    private Http.Request request;

    @BeforeClass
    public static void startApp() {
        app = Helpers.fakeApplication();
        Helpers.start(app);
    }

    @Before
    public void setUp() throws Exception {
        Map<String, String> flashData = Collections.emptyMap();
        Map<String, Object> argData = Collections.emptyMap();
        Long id = 2L;
        play.api.mvc.RequestHeader header = mock(play.api.mvc.RequestHeader.class);
        Http.Context context = new Http.Context(id, header, request, flashData, flashData, argData);
        Http.Context.current.set(context);

    }

    @Test
    public void signingUser() {
        // Try to go to the index page
        browser.goTo(routes.Journeys.rides().url());
        // User is redirected to the login page
        assertThat(browser.url()).isEqualTo(routes.Authentication.login().url());

        // Fill the signing in form
        browser.fill("[name=name]").with("toto");
        browser.fill("[name=password]").with("toto");
        browser.submit("form");
        // User is logged in and redirected to the index page
        // TODO

        assertThat(Authentication.username()).isEqualTo("toto");
        assertThat(browser.url()).isEqualTo(routes.Journeys.rides().url());

        // Logout
        // TODO
        browser.goTo(routes.Authentication.logout().url());
        assertThat(browser.url()).isEqualTo(routes.Authentication.logout().url());
    }
/*
    @Test
    public void invalidLogin() {
        browser.goTo(routes.Journeys.journeys().url());
        // Submit an empty form
        // TODO
        browser.submit("form");

        // Validation error
        assertThat(browser.pageSource()).contains("???");

        // Submit an invalid form
        browser.fill("[name=name]").with("???");
        browser.fill("[name=password]").with("???");
        browser.submit("form");
        assertThat(browser.pageSource()).contains("???");
    }
*/

    @AfterClass
    public static void stopApp() {
        Helpers.stop(app);
    }
}
