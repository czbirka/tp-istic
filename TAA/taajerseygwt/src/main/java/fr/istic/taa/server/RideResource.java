package fr.istic.taa.server;

import fr.istic.taa.shared.IRide;
import fr.istic.taa.shared.IUser;
import fr.istic.taa.shared.Ride;
import fr.istic.taa.shared.User;

import javax.persistence.EntityTransaction;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Created by Thomas & Amona on 06/10/14.
 */
@Path("/rides")
public class RideResource implements IRideResource {


    public RideResource() {}

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<IRide> getRides() {
        return ManagerSingleton.getInstance().createQuery("select r from Ride as r").getResultList();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Ride getRideById(@PathParam("id") String id) {
        return ManagerSingleton.getInstance().find(Ride.class, Integer.parseInt(id));
    }

    @POST
    @Path("/create/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public IRide create(Ride ride) {
        EntityTransaction t = ManagerSingleton.getInstance().getTransaction();

        t.begin();
        User driver = ManagerSingleton.getInstance().find(User.class, ride.getDriver().getId());
        driver.addRidesAsDriver(ride);
        ride.setDriver(driver);
        ManagerSingleton.getInstance().merge(driver);
        t.commit();

        return ride;
    }

    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public IRide update(Ride update) {

        // TODO: fix this (rides are not being updated)
        EntityTransaction t = ManagerSingleton.getInstance().getTransaction();

        t.begin();
        ManagerSingleton.getInstance().merge(update);
        t.commit();

        return update;
    }

    @PUT
    @Path("{rideId}/add/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public IRide addPassenger(@PathParam("rideId") String rideId,@PathParam("userId") String userId) throws Exception {

        EntityTransaction t = ManagerSingleton.getInstance().getTransaction();

        IUser user = ManagerSingleton.getInstance().find(User.class, Integer.parseInt(userId));
        IRide ride = ManagerSingleton.getInstance().find(Ride.class, Integer.parseInt(rideId));

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
        ManagerSingleton.getInstance().merge(user);
        t.commit();

        return ride;
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public IRide deleteById(@PathParam("id") String id) {
        EntityTransaction t = ManagerSingleton.getInstance().getTransaction();
        Ride ride = ManagerSingleton.getInstance().find(Ride.class, Integer.parseInt(id));
        IUser driver = ride.getDriver();

        // Update driver
        t.begin();
        driver.removeRidesAsDriver(ride);
        ManagerSingleton.getInstance().merge(driver);
        t.commit();

        // Delete ride
        t.begin();
        ManagerSingleton.getInstance().remove(ride);
        t.commit();

        return ride;
    }
}
