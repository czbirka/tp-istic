package fr.istic.taa.server;

import fr.istic.taa.shared.Driver;

import javax.persistence.EntityTransaction;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Created by thomas on 23/10/14.
 */
@Path("/drivers")
public class DriverResource implements IDriverResource {

    public DriverResource() {

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Driver> getDrivers() {
        return ManagerSingleton.getInstance().createQuery("select d from Driver as d").getResultList();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Driver getRideById(String id) {
        return ManagerSingleton.getInstance().find(Driver.class, Integer.parseInt(id));
    }

    @POST
    @Path("/create/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Driver> create(Driver ride) {
        EntityTransaction t = ManagerSingleton.getInstance().getTransaction();

        t.begin();
        ManagerSingleton.getInstance().persist(ride);
        t.commit();

        return ManagerSingleton.getInstance().createQuery("select d from Driver as d").getResultList();
    }

    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Driver> update(Driver update) {
        EntityTransaction t = ManagerSingleton.getInstance().getTransaction();

        t.begin();
        ManagerSingleton.getInstance().merge(update);
        t.commit();

        return ManagerSingleton.getInstance().createQuery("select d from Driver as d").getResultList();
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Driver> deleteById(@PathParam("id") String id) {
        EntityTransaction t = ManagerSingleton.getInstance().getTransaction();

        t.begin();
        Driver d = ManagerSingleton.getInstance().find(Driver.class, Integer.parseInt(id));
        ManagerSingleton.getInstance().remove(d);
        t.commit();

        return ManagerSingleton.getInstance().createQuery("select d from Driver as d").getResultList();
    }
}
