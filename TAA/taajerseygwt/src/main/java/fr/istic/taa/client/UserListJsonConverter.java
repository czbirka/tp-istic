package fr.istic.taa.client;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;
import fr.istic.taa.shared.Factory;
import fr.istic.taa.shared.IUserList;

/**
 * Created by thomas & amona on 05/11/14.
 */
public class UserListJsonConverter {
    private UserListJsonConverter() {
    }

    private static UserListJsonConverter instance = new UserListJsonConverter();

    // Instantiate the factory
    Factory factory = GWT.create(Factory.class);
    // In non-GWT code, use AutoBeanFactorySource.create(MyFactory.class);

    String serializeToJson(IUserList userList) {
        // Retrieve the AutoBean controller
        AutoBean<IUserList> bean = AutoBeanUtils.getAutoBean(userList);

        return AutoBeanCodex.encode(bean).getPayload();
    }

    IUserList deserializeFromJson(String json) {
        AutoBean<IUserList> bean = AutoBeanCodex.decode(factory, IUserList.class, "{\"users\": " + json+ "}");
        return bean.as();
    }

    public static UserListJsonConverter getInstance() {
        return instance;
    }
}
