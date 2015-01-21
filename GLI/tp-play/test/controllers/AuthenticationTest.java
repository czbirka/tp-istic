package controllers;

import org.junit.Test;
import play.test.*;

import static org.fest.assertions.Assertions.assertThat;

public class AuthenticationTest extends WithBrowser {

    @Test
    public void signingUser() {

        // Try to go to the index page
        browser.goTo(routes.Journeys.rides().url());
        // User is redirected to the login page
        assertThat(browser.url()).isEqualTo(routes.Authentication.login().url());

        // Fill the signing in form
        browser.fill("[name=username]").with("admin");
        browser.fill("[name=password]").with("admin");
        browser.submit("form");
        // User is logged in and redirected to the index page
        // TODO

        assertThat(browser.url()).isEqualTo(routes.Journeys.rides().url());
            assertThat(browser.$("#currentUser", 0).getText()).isEqualTo("admin");

        // Logout
        // TODO
        browser.goTo(routes.Authentication.logout().url());
        assertThat(browser.url()).isEqualTo(routes.Authentication.login().url());
    }

    @Test
    public void invalidLogin() {
        browser.goTo(routes.Journeys.rides().url());
        // Submit an empty form
        // TODO
        browser.submit("form");

        // Validation error
        assertThat(browser.pageSource()).contains("has-error");

        // Submit an invalid form
        browser.fill("[name=username]").with("admin");
        browser.fill("[name=password]").with("1234");
        browser.submit("form");
        assertThat(browser.pageSource()).contains("has-error");
    }
}
