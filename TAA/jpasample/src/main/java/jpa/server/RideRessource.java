package jpa.server;

import jpa.domain.Driver;
import jpa.domain.IRide;
import jpa.domain.Passenger;
import jpa.domain.Ride;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Thomas & Amona on 06/10/14.
 */
@Path("/rides")
public class RideRessource implements IRideRessource {

    EntityManager manager;

    public RideRessource() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("dev");
        manager = factory.createEntityManager();
        EntityTransaction t = manager.getTransaction();
        t.begin();

        Ride r = new Ride();
        r.setOrigin("NY");
        r.setDestination("LA");
        r.setSeatNumber(2);
        r.setLeavingDate(new Date(new java.util.Date().getTime()));

        // Persist the objects
        manager.persist(r);
        t.commit();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Ride> getRides() {
        return manager.createQuery("select r from Ride as r").getResultList();
    }

    @GET
    @Path("/search/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Ride getRideById(@PathParam("id") String id) {
        return manager.find(Ride.class, Integer.parseInt(id));
    }

    @POST
    @Path("/create/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Ride> create(Ride ride) {
        EntityTransaction t = manager.getTransaction();

        t.begin();
        manager.persist(ride);
        t.commit();

        return manager.createQuery("select r from Ride as r").getResultList();
    }

    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Ride>  update(Ride update) {
        EntityTransaction t = manager.getTransaction();

        t.begin();
        manager.refresh(update);
        t.commit();

        return manager.createQuery("select r from Ride as r").getResultList();
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Ride> deleteById(@PathParam("id") String id) {
        EntityTransaction t = manager.getTransaction();

        t.begin();
        Ride r = manager.find(Ride.class, Integer.parseInt(id));
        manager.remove(r);
        t.commit();

        return manager.createQuery("select r from Ride as r").getResultList();
    }
}
