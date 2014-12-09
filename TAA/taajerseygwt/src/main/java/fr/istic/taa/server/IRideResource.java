package fr.istic.taa.server;


import fr.istic.taa.shared.IRide;
import fr.istic.taa.shared.Ride;

import java.util.Collection;

/**
 * Created by Thomas & Amona on 06/10/14.
 */
public interface IRideResource {

    Collection<IRide> getRides();

    IRide getRideById(String id);

    IRide create(Ride ride);

    IRide update(Ride update);

    IRide addPassenger(String rideId, String userId) throws Exception;

    IRide deleteById(String id);
}
