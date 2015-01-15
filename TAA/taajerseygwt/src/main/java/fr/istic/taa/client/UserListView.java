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

    ListDataProvider<IUser> userListDataProvider;

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
        userListDataProvider = new ListDataProvider<IUser>();

        usersTable.setEmptyTableWidget(new Label("There is no user."));

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

        ButtonCell deleteBtn = new ButtonCell(IconType.TRASH, ButtonType.DANGER);

        Column<IUser, String> actionColumn = new Column<IUser, String>(deleteBtn) {
            @Override
            public String getValue(IUser user) {
                return "Delete";
            }
        };

        actionColumn.setFieldUpdater(new FieldUpdater<IUser, String>() {
            public void update(final int index, IUser user, String value) {
                RequestBuilder deleteRq = new RequestBuilder(RequestBuilder.DELETE, GWT.getHostPageBaseURL() + "rest/users/delete/" + user.getId());

                deleteRq.setCallback(new RequestCallback() {
                    public void onResponseReceived(Request request, Response response) {
                        userListDataProvider.getList().remove(index);
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

        usersTable.addColumn(idColumn, "id");
        usersTable.addColumn(usernameColumn, "username");
        usersTable.addColumn(actionColumn, "action");
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

                    userListDataProvider.setList(users);
                    userListDataProvider.addDataDisplay(usersTable);
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
