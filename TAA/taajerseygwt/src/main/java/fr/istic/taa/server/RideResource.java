package fr.istic.taa.server;

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


    public RideResource() {

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Ride> getRides() {
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
    public Collection<Ride> create(Ride ride) {
        EntityTransaction t = ManagerSingleton.getInstance().getTransaction();

        t.begin();
        User driver = (User) ManagerSingleton.getInstance()
                .createQuery("select u from User as u where u.id=" + ride.getDriver().getId())
                .getSingleResult();
        driver.addRidesAsDriver(ride);
        ride.setDriver(driver);
        ManagerSingleton.getInstance().merge(driver);
        t.commit();

        return ManagerSingleton.getInstance().createQuery("select r from Ride as r").getResultList();
    }

    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Ride> update(Ride update) {
        EntityTransaction t = ManagerSingleton.getInstance().getTransaction();

        t.begin();
        ManagerSingleton.getInstance().merge(update);
        t.commit();

        return ManagerSingleton.getInstance().createQuery("select r from Ride as r").getResultList();
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Ride> deleteById(@PathParam("id") String id) {
        EntityTransaction t = ManagerSingleton.getInstance().getTransaction();
        Ride r = ManagerSingleton.getInstance().find(Ride.class, Integer.parseInt(id));
        User driver = r.getDriver();

        // Update driver
        t.begin();
        driver.removeRidesAsDriver(r);
        ManagerSingleton.getInstance().merge(driver);
        t.commit();

        // Delete ride
        t.begin();
        ManagerSingleton.getInstance().remove(r);
        t.commit();

        return ManagerSingleton.getInstance().createQuery("select r from Ride as r").getResultList();
    }
}
