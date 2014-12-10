package fr.istic.taa.server;

import fr.istic.taa.shared.IRide;
import fr.istic.taa.shared.IUser;
import fr.istic.taa.shared.Ride;
import fr.istic.taa.shared.User;

import javax.persistence.EntityTransaction;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Thomas & Amona on 06/10/14.
 */
@Path("/rides")
public class RideResource implements IRideResource {

    private static ManagerSingleton manager = ManagerSingleton.getInstance();

    public RideResource() {}

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<IRide> getRides() {
        return manager.createQuery("select r from Ride as r").getResultList();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Ride getRideById(@PathParam("id") String id) {
        return manager.find(Ride.class, Integer.parseInt(id));
    }

    @POST
    @Path("/create/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public IRide create(Ride ride) {
        EntityTransaction t = manager.getTransaction();

        t.begin();

        // FetchType.EAGER on user
        // We can update it without getting it with the manager
        IUser user = ride.getDriver();
        user.addRidesAsDriver(ride);

        manager.merge(ride);
        t.commit();

        return ride;
    }

    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public IRide update(Ride update) {

        // TODO: fix this (rides are not being updated)
        EntityTransaction t = manager.getTransaction();

        t.begin();
        manager.merge(update);
        t.commit();

        return update;
    }

    @PUT
    @Path("{rideId}/add/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public IRide addPassenger(@PathParam("rideId") String rideId,@PathParam("userId") String userId) throws Exception {

        EntityTransaction t = manager.getTransaction();

        IUser user = manager.find(User.class, Integer.parseInt(userId));
        IRide ride = manager.find(Ride.class, Integer.parseInt(rideId));

        if (ride.getSeatNumber() == 0) {
            throw new RuntimeException();
        }

        if (ride.getDriver().getId() == user.getId()) {
            throw new RuntimeException();
        }

        for(IUser u : ride.getPassengers()) {
            if (u.getId() == user.getId()) {
                throw new RuntimeException();
            }
        }

        user.addRidesAsPassenger(ride);
        ride.addPassenger(user);
        ride.setSeatNumber(ride.getSeatNumber() -1);

        t.begin();
        manager.merge(user);
        t.commit();

        return ride;
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public IRide deleteById(@PathParam("id") String id) {
        EntityTransaction t = manager.getTransaction();
        Ride ride = manager.find(Ride.class, Integer.parseInt(id));
        IUser driver = ride.getDriver();

        t.begin();

        // Update every passengers
        for (IUser passenger : ride.getPassengers()) {
            passenger.removeRidesAsPassenger(ride);
            manager.merge(passenger);
        }

        // Update driver
        driver.removeRidesAsDriver(ride);
        manager.merge(driver);

        // Delete ride
        manager.remove(ride);

        t.commit();

        return ride;
    }
}
