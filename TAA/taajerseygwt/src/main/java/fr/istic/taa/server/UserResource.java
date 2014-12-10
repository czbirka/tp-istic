package fr.istic.taa.server;

import fr.istic.taa.shared.IUser;
import fr.istic.taa.shared.User;

import javax.persistence.EntityTransaction;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Thomas & Amona on 23/10/14.
 */
@Path("/users")
public class UserResource implements IUserResource {

    public UserResource() {}

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<IUser> getUsers() {
        return ManagerSingleton.getInstance().createQuery("select u from User as u").getResultList();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public IUser getUserById(@PathParam("id") String id) {
        return ManagerSingleton.getInstance().find(User.class, Integer.parseInt(id));
    }

    @GET
    @Path("/search/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public IUser getUserByName(@PathParam("name") String name) {
        List<IUser> users = (List<IUser>) ManagerSingleton.getInstance().createQuery("select u from User as u where u.username = '" + name + "'").getResultList();

        if (users.isEmpty())
            return null;

        return users.get(0);
    }

    @POST
    @Path("/create/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public IUser create(User user) {
        EntityTransaction t = ManagerSingleton.getInstance().getTransaction();

        t.begin();
        ManagerSingleton.getInstance().persist(user);
        t.commit();

        return user;
    }

    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public IUser update(User update) {
        EntityTransaction t = ManagerSingleton.getInstance().getTransaction();

        t.begin();
        ManagerSingleton.getInstance().merge(update);
        t.commit();

        return update;
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public IUser deleteById(@PathParam("id") String id) {
        EntityTransaction t = ManagerSingleton.getInstance().getTransaction();

        t.begin();
        User user = ManagerSingleton.getInstance().find(User.class, Integer.parseInt(id));
        ManagerSingleton.getInstance().remove(user);
        t.commit();

        return user;
    }
}
