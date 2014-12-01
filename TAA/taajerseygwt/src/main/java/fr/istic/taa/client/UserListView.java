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
import fr.istic.taa.shared.IUser;
import fr.istic.taa.shared.IUserList;

import java.util.List;

/**
 * Created by thomas & amona on 06/11/14.
 */
public class UserListView extends Composite {

    private static UserListUiBinder uiBinder = GWT.create(UserListUiBinder.class);

    @UiField(provided = true)
    CellTable<IUser> usersTable;

    @UiTemplate("UserList.ui.xml")
    interface UserListUiBinder extends UiBinder<Widget, UserListView> {

    }

    public UserListView() {
        createUserListTable();
        initWidget(uiBinder.createAndBindUi(this));
        buildPage();
    }

    private void createUserListTable() {
        usersTable = new CellTable<IUser>();

        TextColumn<IUser> idColumn = new TextColumn<IUser>() {
            @Override
            public String getValue(IUser user) {
                return "" + user.getId();
            }
        };

        TextColumn<IUser> usernameColumn = new TextColumn<IUser>() {
            @Override
            public String getValue(IUser user) {
                return user.getUsername();
            }
        };

        usersTable.addColumn(idColumn, "id");
        usersTable.addColumn(usernameColumn, "username");
    }

    private void buildPage() {
        RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, GWT.getHostPageBaseURL() + "rest/users/");

        rb.setCallback(new RequestCallback() {
            public void onError(Request request, Throwable exception) {
                Window.alert(exception.getMessage());
            }

            public void onResponseReceived(Request request,
                                           Response response) {
                if (200 == response.getStatusCode()) {
                    IUserList userList = UserListJsonConverter.getInstance()
                            .deserializeFromJson(response.getText());

                    List<IUser> users = userList.getUsers();

                    ListDataProvider<IUser> dataProvider = new ListDataProvider<IUser>();
                    dataProvider.setList(users);
                    dataProvider.addDataDisplay(usersTable);
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
