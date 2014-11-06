package fr.istic.taa.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.History;
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
public class TaaJerseyGwt implements EntryPoint, ValueChangeHandler<String> {

    private static final String HOME = "home";

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

    public void onValueChange(ValueChangeEvent<String> stringValueChangeEvent) {
        String history = stringValueChangeEvent.getValue();
        RootPanel.get().clear();

        if (history.equals(HOME)) {
            loadHomePage();
        }
    }
}
