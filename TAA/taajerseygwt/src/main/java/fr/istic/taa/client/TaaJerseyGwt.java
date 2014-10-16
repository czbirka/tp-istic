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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import fr.istic.taa.shared.IRide;

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
		area.setValue("2");
		RootPanel.get().add(area);

		// Create a button
		com.google.gwt.user.client.ui.Button b = new Button();
		b.setText("test call json restful service");

		RootPanel.get().add(b);

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
							Window.alert("get the ride from :" + r.getDriver());

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
