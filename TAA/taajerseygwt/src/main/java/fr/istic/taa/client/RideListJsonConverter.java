package fr.istic.taa.client;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;
import fr.istic.taa.shared.Factory;
import fr.istic.taa.shared.IRideList;

/**
 * Created by thomas on 05/11/14.
 */
public class RideListJsonConverter {

    private RideListJsonConverter() {
    }

    private static RideListJsonConverter instance = new RideListJsonConverter();

    // Instantiate the factory
    Factory factory = GWT.create(Factory.class);
    // In non-GWT code, use AutoBeanFactorySource.create(MyFactory.class);

    String serializeToJson(IRideList rideList) {
        // Retrieve the AutoBean controller
        AutoBean<IRideList> bean = AutoBeanUtils.getAutoBean(rideList);

        return AutoBeanCodex.encode(bean).getPayload();
    }

    IRideList deserializeFromJson(String json) {
        AutoBean<IRideList> bean = AutoBeanCodex.decode(factory, IRideList.class, "{\"rides\": " + json+ "}");
        return bean.as();
    }

    public static RideListJsonConverter getInstance() {
        return instance;
    }
}
