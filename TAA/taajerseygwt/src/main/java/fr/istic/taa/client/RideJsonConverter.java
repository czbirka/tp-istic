package fr.istic.taa.client;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;

import fr.istic.taa.shared.Factory;
import fr.istic.taa.shared.IRide;

public class RideJsonConverter {

	private RideJsonConverter() {
	}

	private static RideJsonConverter instance = new RideJsonConverter();

	// Instantiate the factory
	Factory factory = GWT.create(Factory.class);
	// In non-GWT code, use AutoBeanFactorySource.create(MyFactory.class);

	public IRide makeRide() {
		// Construct the AutoBean
		AutoBean<IRide> ride = factory.ride();

		// Return the Ride interface shim
		return ride.as();
	}

	String serializeToJson(IRide ride) {
		// Retrieve the AutoBean controller
		AutoBean<IRide> bean = AutoBeanUtils.getAutoBean(ride);

		return AutoBeanCodex.encode(bean).getPayload();
	}

	IRide deserializeFromJson(String json) {
		AutoBean<IRide> bean = AutoBeanCodex.decode(factory, IRide.class, json);
		return bean.as();
	}

	public static RideJsonConverter getInstance() {
		return instance;
	}
}
