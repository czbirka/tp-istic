package fr.istic.taa.client;


import com.github.gwtbootstrap.client.ui.CellTable;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import fr.istic.taa.shared.IRide;
import fr.istic.taa.shared.IRideList;

import java.util.List;

/**
 * Created by thomas on 05/11/14.
 */
public class HomeView extends Composite {
    private static HomeUiBinder uiBinder = GWT.create(HomeUiBinder.class);

    @UiField(provided = true)
    CellTable<IRide> rideTable;

    @UiTemplate("Home.ui.xml")
    interface HomeUiBinder extends UiBinder<Widget, HomeView> {}

    public HomeView() {
        createRideTable();
        initWidget(uiBinder.createAndBindUi(this));
        buildPage();
    }

    private void createRideTable() {
        rideTable = new CellTable<IRide>();

        TextColumn<IRide> idColumn = new TextColumn<IRide>() {
            @Override
            public String getValue(IRide ride) {
                return "" + ride.getId();
            }
        };

        TextColumn<IRide> originColumn = new TextColumn<IRide>() {
            @Override
            public String getValue(IRide ride) {
                return ride.getOrigin();
            }
        };

        TextColumn<IRide> destinationColumn = new TextColumn<IRide>() {
            @Override
            public String getValue(IRide ride) {
                return ride.getDestination();
            }
        };

        TextColumn<IRide> leavingDateColumn = new TextColumn<IRide>() {
            @Override
            public String getValue(IRide ride) {
                return ride.getLeavingDate().toString();
            }
        };

        TextColumn<IRide> seatNumberColumn = new TextColumn<IRide>() {
            @Override
            public String getValue(IRide ride) {
                return "" + ride.getSeatNumber();
            }
        };

        TextColumn<IRide> driverColumn = new TextColumn<IRide>() {
            @Override
            public String getValue(IRide ride) {
                return ride.getDriver().getUsername();
            }
        };

        rideTable.addColumn(idColumn, "id");
        rideTable.addColumn(originColumn, "origin");
        rideTable.addColumn(destinationColumn, "destination");
        rideTable.addColumn(leavingDateColumn, "leaving date");
        rideTable.addColumn(seatNumberColumn, "seat number");
        rideTable.addColumn(driverColumn, "driver");
    }

    private void buildPage() {
        RequestBuilder rb = new RequestBuilder(RequestBuilder.GET,
                GWT.getHostPageBaseURL() + "rest/rides/");
        rb.setCallback(new RequestCallback() {
            public void onError(Request request, Throwable exception) {
                Window.alert(exception.getMessage());
            }

            public void onResponseReceived(Request request,
                                           Response response) {
                if (200 == response.getStatusCode()) {
                    IRideList rideList = RideListJsonConverter.getInstance()
                            .deserializeFromJson(response.getText());

                    List<IRide> rides = rideList.getRides();

                    ListDataProvider<IRide> dataProvider = new ListDataProvider<IRide>();
                    dataProvider.setList(rides);
                    dataProvider.addDataDisplay(rideTable);
                }
            }
        });
        try {
            rb.send();
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }
}
