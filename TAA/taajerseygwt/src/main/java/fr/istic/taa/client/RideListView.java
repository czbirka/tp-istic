package fr.istic.taa.client;


import com.github.gwtbootstrap.client.ui.ButtonCell;
import com.github.gwtbootstrap.client.ui.CellTable;
import com.github.gwtbootstrap.client.ui.Label;
import com.github.gwtbootstrap.client.ui.constants.ButtonType;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import fr.istic.taa.shared.IRide;
import fr.istic.taa.shared.IRideList;

import java.util.List;

/**
 * Created by thomas & amona on 05/11/14.
 */
public class RideListView extends Composite {
    private static HomeUiBinder uiBinder = GWT.create(HomeUiBinder.class);

    @UiField(provided = true)
    CellTable<IRide> rideTable;

    ListDataProvider<IRide> rideListDataProvider;

    @UiTemplate("RideList.ui.xml")
    interface HomeUiBinder extends UiBinder<Widget, RideListView> {}

    public RideListView() {
        createRideTable();
        initWidget(uiBinder.createAndBindUi(this));
        buildPage();
    }

    private void createRideTable() {
        rideTable = new CellTable<IRide>();
        rideListDataProvider = new ListDataProvider<IRide>();

        rideTable.setEmptyTableWidget(new Label("There is no ride."));

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

        ButtonCell deleteBtn = new ButtonCell(IconType.TRASH, ButtonType.DANGER);

        Column<IRide, String> actionColumn = new Column<IRide, String>(deleteBtn) {
            @Override
            public String getValue(IRide ride) {
                return "Delete";
            }
        };

        actionColumn.setFieldUpdater(new FieldUpdater<IRide, String>() {
            public void update(final int index, IRide ride, String value) {
                RequestBuilder deleteRq = new RequestBuilder(RequestBuilder.DELETE,
                        GWT.getHostPageBaseURL() + "rest/rides/delete/" + ride.getId());

                deleteRq.setCallback(new RequestCallback() {
                    public void onResponseReceived(Request request, Response response) {
                        rideListDataProvider.getList().remove(index);
                    }

                    public void onError(Request request, Throwable exception) {
                        Window.alert(exception.getMessage());
                    }
                });

                try {
                    deleteRq.send();
                } catch (RequestException e) {
                    e.printStackTrace();
                }
            }
        });

        rideTable.addColumn(idColumn, "id");
        rideTable.addColumn(originColumn, "origin");
        rideTable.addColumn(destinationColumn, "destination");
        rideTable.addColumn(leavingDateColumn, "leaving date");
        rideTable.addColumn(seatNumberColumn, "seat number");
        rideTable.addColumn(driverColumn, "driver");
        rideTable.addColumn(actionColumn, "action");
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

                    rideListDataProvider.setList(rides);
                    rideListDataProvider.addDataDisplay(rideTable);
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
