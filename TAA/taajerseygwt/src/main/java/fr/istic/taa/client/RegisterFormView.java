package fr.istic.taa.client;

import com.github.gwtbootstrap.client.ui.Form;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import fr.istic.taa.shared.IUser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thomas & amona on 08/11/14.
 */
public class RegisterFormView extends Composite {
    private static RegisterFormUiBinder uiBinder = GWT.create(RegisterFormUiBinder.class);

    private Map<String, IUser> usersMap;

    @UiField
    TextBox username;

    @UiField
    TextBox password;

    @UiField
    Form registerForm;

    public RegisterFormView() {
        usersMap = new HashMap<String, IUser>();

        initWidget(uiBinder.createAndBindUi(this));
        buildPage();
    }

    @UiTemplate("RegisterForm.ui.xml")
    interface RegisterFormUiBinder extends UiBinder<Widget, RegisterFormView> {}

    private void buildPage() {
        registerForm.addSubmitHandler(new Form.SubmitHandler() {
            public void onSubmit(Form.SubmitEvent submitEvent) {
                String usernameValue = username.getValue();
                String passwordValue = password.getValue();

                if (usernameValue.length() == 0 || passwordValue.length() == 0) {
                    Window.alert("Please fill all the required fields!");
                } else {
                    IUser user = UserJsonConverter.getInstance().makeUser();

                    user.setUsername(usernameValue);
                    user.setPassword(passwordValue);

                    String serializedUser = UserJsonConverter.getInstance().serializeToJson(user);

                    RequestBuilder rb = new RequestBuilder(RequestBuilder.POST,
                            GWT.getHostPageBaseURL() + "rest/users/create");

                    rb.setHeader("Content-Type", "application/json");
                    rb.setRequestData(serializedUser);

                    rb.setCallback(new RequestCallback() {
                        public void onResponseReceived(Request request, Response response) {
                            if (response.getStatusCode() == 200) {
                                Window.Location.replace("#users");
                            }
                        }

                        public void onError(Request request, Throwable exception) {
                            Window.alert(exception.getMessage());
                        }
                    });

                    try {
                        rb.send();
                    } catch (RequestException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
