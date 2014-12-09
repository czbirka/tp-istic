package fr.istic.taa.client;

import com.github.gwtbootstrap.client.ui.Form;
import com.github.gwtbootstrap.client.ui.ListBox;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.datepicker.client.ui.DateBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import fr.istic.taa.shared.IRide;
import fr.istic.taa.shared.IUser;
import fr.istic.taa.shared.IUserList;
import fr.istic.taa.shared.Ride;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by thomas & amona on 08/11/14.
 */
public class RideFormView extends Composite {
    private static RideFormUiBinder uiBinder = GWT.create(RideFormUiBinder.class);

    private Map<String, IUser> usersMap;

    @UiField
    TextBox origin;

    @UiField
    TextBox destination;

    @UiField
    DateBox leavingDate;

    @UiField
    TextBox seatNumber;

    @UiField
    Form rideForm;

    @UiField
    ListBox driverList;

    public RideFormView() {
        usersMap = new HashMap<String, IUser>();

        createUserList();
        initWidget(uiBinder.createAndBindUi(this));
        buildPage();
    }

    @UiTemplate("RideForm.ui.xml")
    interface RideFormUiBinder extends UiBinder<Widget, RideFormView> {}

    public void createUserList() {
        RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, GWT.getHostPageBaseURL() + "rest/users");

        rb.setCallback(new RequestCallback() {
            public void onResponseReceived(Request request, Response response) {
                if (response.getStatusCode() == 200) {
                    IUserList userList = UserListJsonConverter.getInstance().deserializeFromJson(response.getText());

                    for (IUser user : userList.getUsers()) {
                        usersMap.put(user.getUsername(), user);
                        driverList.addItem(user.getUsername());
                    }
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

    private void buildPage() {
        rideForm.addSubmitHandler(new Form.SubmitHandler() {
            public void onSubmit(Form.SubmitEvent submitEvent) {
                String originValue = origin.getValue();
                String destinationValue = destination.getValue();
                Date leavingDateValue = leavingDate.getValue();
                String seatNumberValue = seatNumber.getValue();
                String driverUsername = driverList.getValue();

                if (originValue.length() == 0
                        || destinationValue.length() == 0
                        || seatNumberValue.length() == 0) {
                    Window.alert("Please fill all the required fields!");
                } else {
                    IRide ride = new Ride();

                    ride.setOrigin(originValue);
                    ride.setDestination(destinationValue);
                    ride.setLeavingDate(leavingDateValue);
                    ride.setSeatNumber(Integer.parseInt(seatNumberValue));
                    ride.setDriver(usersMap.get(driverUsername));

                    String serializedRide = RideJsonConverter.getInstance().serializeToJson(ride);

                    Window.alert(serializedRide);
                    RequestBuilder rb = new RequestBuilder(RequestBuilder.POST,
                            GWT.getHostPageBaseURL() + "rest/rides/create");

                    rb.setHeader("Content-Type", "application/json");
                    rb.setRequestData(serializedRide);

                    rb.setCallback(new RequestCallback() {
                        public void onResponseReceived(Request request, Response response) {
                            if (response.getStatusCode() == 200) {
                                Window.alert("Success!");
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
