package fr.istic.taa.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.ListDataProvider;
import fr.istic.taa.shared.IRide;
import fr.istic.taa.shared.Ride;

import javax.swing.text.TableView;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TaaJerseyGwt implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		// Create a text
		final TextBox area = new TextBox();
		area.setValue("1");
		RootPanel.get().add(area);

		// Create a button
		Button b = new Button();
		b.setText("test call json restful service");

		RootPanel.get().add(b);

        CellTable<Ride> table = new CellTable<Ride>();

        TextColumn<Ride> idColumn = new TextColumn<Ride>() {
            @Override
            public String getValue(Ride ride) {
                return "" + ride.getId();
            }
        };
        TextColumn<Ride> originColumn = new TextColumn<Ride>() {
            @Override
            public String getValue(Ride ride) {
                return ride.getOrigin();
            }
        };
        TextColumn<Ride> destColumn = new TextColumn<Ride>() {
            @Override
            public String getValue(Ride ride) {
                return ride.getDestination();
            }
        };
        TextColumn<Ride> leavingDateColumn = new TextColumn<Ride>() {
            @Override
            public String getValue(Ride ride) {
                return ride.getLeavingDate().toString();
            }
        };
        TextColumn<Ride> seatNumberColumn = new TextColumn<Ride>() {
            @Override
            public String getValue(Ride ride) {
                return "" + ride.getSeatNumber();
            }
        };
        TextColumn<Ride> driverColumn = new TextColumn<Ride>() {
            @Override
            public String getValue(Ride ride) {
                return ride.getDriver().toString();
            }
        };
        TextColumn<Ride> passengersColumn = new TextColumn<Ride>() {
            @Override
            public String getValue(Ride ride) {
                return ride.getPassengers().toString();
            }
        };

        table.addColumn(idColumn, "Id");
        table.addColumn(originColumn, "Origin");
        table.addColumn(destColumn, "Destination");
        table.addColumn(leavingDateColumn, "Leaving Date");
        table.addColumn(seatNumberColumn, "Seat Number");
        table.addColumn(driverColumn, "Driver");
        table.addColumn(passengersColumn, "Passengers");

        final ListDataProvider<Ride> dataProvider = new ListDataProvider<Ride>();
        dataProvider.addDataDisplay(table);


        RootPanel.get().add(table);

		b.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, GWT
						.getHostPageBaseURL() + "rest/rides/search/" + area.getText());
				rb.setCallback(new RequestCallback() {

					public void onError(Request request, Throwable exception) {
						Window.alert(exception.getMessage());
					}

					public void onResponseReceived(Request request,
							Response response) {
						if (200 == response.getStatusCode()) {
							IRide r = RideJsonConverter.getInstance()
									.deserializeFromJson(response.getText());
							Window.alert("get the ride from :" + r.getDestination());
                            dataProvider.getList().add((Ride)r);
						}
					}
				});
				try {
					rb.send();
				} catch (RequestException e) {
					e.printStackTrace();
				}

			}
		});
	}
}
