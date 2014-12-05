package fr.istic.taa.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TaaJerseyGwt implements EntryPoint, ValueChangeHandler<String> {

    private static final String HOME = "home";
    private static final String USERS = "users";
    private static final String CREATE_RIDE = "create";
    private static final String REGISTER = "register";


	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
        initialize();
	}

    public void initialize() {
        History.newItem(HOME, true);
        History.addValueChangeHandler(this);
        loadHomePage();
    }

    public void loadHomePage() {
        RootPanel.get().add(new HomeView());
    }

    public void loadUsersPage() {
        RootPanel.get().add(new UserListView());
    }

    public void loadCreateRidePage() {
        RootPanel.get().add(new RideFormView());
    }

    public void loadRegisterPage() {
        RootPanel.get().add(new RegisterFormView());
    }

    public void onValueChange(ValueChangeEvent<String> stringValueChangeEvent) {
        String history = stringValueChangeEvent.getValue();
        RootPanel.get().clear();

        if (history.equals(USERS)) {
            loadUsersPage();
        } else if (history.equals(CREATE_RIDE)) {
            loadCreateRidePage();
        } else if (history.equals(REGISTER)) {
            loadRegisterPage();
        } else {
            loadHomePage();
        }
    }

}
