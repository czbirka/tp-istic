package fr.istic.taa.server;


import fr.istic.taa.shared.IRide;
import fr.istic.taa.shared.Ride;
import fr.istic.taa.shared.User;

import java.util.List;

/**
 * Created by Thomas & Amona on 06/10/14.
 */
public interface IRideResource {

    List<IRide> getRides();

    IRide getRideById(String id);

    IRide create(Ride ride);

    IRide update(Ride update);

    IRide addPassenger(String rideId, User passenger) throws Exception;

    IRide deleteById(String id);
}
