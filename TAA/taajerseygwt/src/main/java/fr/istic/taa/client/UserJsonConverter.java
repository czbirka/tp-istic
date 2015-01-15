package fr.istic.taa.client;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;
import fr.istic.taa.shared.Factory;
import fr.istic.taa.shared.IUser;

/**
 * Created by thomas & amona on 05/11/14.
 */
public class UserJsonConverter {

    private UserJsonConverter() {}

    private static UserJsonConverter instance = new UserJsonConverter();

    // Instantiate the factory
    Factory factory = GWT.create(Factory.class);
    // In non-GWT code, use AutoBeanFactorySource.create(MyFactory.class);

    public IUser makeUser() {
        // Construct the AutoBean
        AutoBean<IUser> user = factory.user();

        // Return the User interface shim
        return user.as();
    }

    String serializeToJson(IUser user) {
        // Retrieve the AutoBean controller
        AutoBean<IUser> bean = AutoBeanUtils.getAutoBean(user);

        return AutoBeanCodex.encode(bean).getPayload();
    }

    IUser deserializeFromJson(String json) {
        AutoBean<IUser> bean = AutoBeanCodex.decode(factory, IUser.class, json);
        return bean.as();
    }

    public static UserJsonConverter getInstance() {
        return instance;
    }

}
