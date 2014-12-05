package fr.istic.taa.server;


import fr.istic.taa.shared.Ride;

import javax.ws.rs.PathParam;
import java.util.Collection;

/**
 * Created by Thomas & Amona on 06/10/14.
 */
public interface IRideResource {

    Collection<Ride> getRides();

    Ride getRideById(String id);

    Collection<Ride> create(Ride ride);

    Collection<Ride> update(Ride update);

    Collection<Ride> addPassenger(String rideId, String userId);

    Collection<Ride> deleteById(String id);
}
