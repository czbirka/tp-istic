package jpa.server;

import jpa.domain.IRide;
import jpa.domain.Ride;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

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
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Collection<Ride> getRides() {
        return manager.createQuery("select r from Ride as l").getResultList();
    }

    @GET
    @Path("/search/{id}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Ride getRideById(@PathParam("id") String id) {
        return manager.find(Ride.class, Integer.parseInt(id));
    }

    @POST
    @Path("/create/")
    @Produces({ MediaType.APPLICATION_JSON })
    public Ride create(JAXBElement<Ride> ride) {
        EntityTransaction t = manager.getTransaction();
        Ride update = ride.getValue();

        t.begin();
        manager.persist(update);
        t.commit();

        return manager.find(Ride.class, update.getId());
    }

    @PUT
    @Path("/update/{id}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Ride update(JAXBElement<Ride> ride) {
        EntityTransaction t = manager.getTransaction();
        Ride update = ride.getValue();

        t.begin();
        manager.refresh(update);
        t.commit();

        return manager.find(Ride.class, update.getId());
    }

    @DELETE
    @Path("/delete/{id}")
    public IRide deleteById(@PathParam("id") String id) {
        EntityTransaction t = manager.getTransaction();

        t.begin();
        Ride r = manager.find(Ride.class, Integer.parseInt(id));
        manager.remove(r);
        t.commit();

        return r;
    }
}
