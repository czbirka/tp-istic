package fr.istic.taa.server;

import fr.istic.taa.shared.User;

import javax.persistence.EntityTransaction;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Created by Thomas & Amona on 23/10/14.
 */
@Path("/users")
public class UserResource implements IUserResource {

    public UserResource() {

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<User> getUsers() {
        return ManagerSingleton.getInstance().createQuery("select u from User as u").getResultList();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserById(@PathParam("id") String id) {
        return ManagerSingleton.getInstance().find(User.class, Integer.parseInt(id));
    }

    @POST
    @Path("/create/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<User> create(User user) {
        EntityTransaction t = ManagerSingleton.getInstance().getTransaction();

        t.begin();
        ManagerSingleton.getInstance().persist(user);
        t.commit();

        return ManagerSingleton.getInstance().createQuery("select u from User as u").getResultList();
    }

    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<User> update(User update) {
        EntityTransaction t = ManagerSingleton.getInstance().getTransaction();

        t.begin();
        ManagerSingleton.getInstance().merge(update);
        t.commit();

        return ManagerSingleton.getInstance().createQuery("select u from User as u").getResultList();
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<User> deleteById(@PathParam("id") String id) {
        EntityTransaction t = ManagerSingleton.getInstance().getTransaction();

        t.begin();
        User d = ManagerSingleton.getInstance().find(User.class, Integer.parseInt(id));
        ManagerSingleton.getInstance().remove(d);
        t.commit();

        return ManagerSingleton.getInstance().createQuery("select u from User as u").getResultList();
    }
}
