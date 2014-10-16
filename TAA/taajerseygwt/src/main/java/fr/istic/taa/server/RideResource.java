package fr.istic.taa.server;

import fr.istic.taa.shared.Ride;

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
    @Path("/search/{id}")
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
        ManagerSingleton.getInstance().persist(ride);
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

        t.begin();
        Ride r = ManagerSingleton.getInstance().find(Ride.class, Integer.parseInt(id));
        ManagerSingleton.getInstance().remove(r);
        t.commit();

        return ManagerSingleton.getInstance().createQuery("select r from Ride as r").getResultList();
    }
}
