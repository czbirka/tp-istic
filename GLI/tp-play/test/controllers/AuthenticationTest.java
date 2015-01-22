package controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.test.*;

import static org.fest.assertions.Assertions.assertThat;

public class AuthenticationTest extends WithBrowser {

    private final String username = "TARS";
    private final String password = "tars";

    @Test
    public void signingUser() {
        // Try to go to the index page
        browser.goTo(routes.Journeys.rides().url());
        // User is redirected to the login page
        assertThat(browser.url()).isEqualTo(routes.Authentication.login().url());

        // Fill the signing in form
        browser.fill("[name=username]").with(username);
        browser.fill("[name=password]").with(password);
        browser.submit("form");

        // User is logged in and redirected to the index page
        assertThat(browser.url()).isEqualTo(routes.Journeys.rides().url());
        assertThat(browser.$("#currentUser", 0).getText()).isEqualTo(username);

        // Logout
        browser.goTo(routes.Authentication.logout().url());
        assertThat(browser.url()).isEqualTo(routes.Authentication.login().url());
    }

    @Test
    public void emptyLogin() {
        browser.goTo(routes.Journeys.rides().url());

        // Submit an empty form
        browser.submit("form");
        assertThat(browser.url()).isEqualTo(routes.Authentication.login().url());

        // Validation error
        assertThat(browser.pageSource()).contains("has-error");
    }
    
    @Test
    public void invalidLogin() {
        browser.goTo(routes.Journeys.rides().url());
        
        // Submit an invalid form
        browser.fill("[name=username]").with(username);
        browser.fill("[name=password]").with("ABC");
        browser.submit("form");
        assertThat(browser.url()).isEqualTo(routes.Authentication.login().url());

        // Validation error
        assertThat(browser.pageSource()).contains("has-error");
    }
}
