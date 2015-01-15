package fr.istic.taa.server;

import fr.istic.taa.shared.IRide;
import fr.istic.taa.shared.IUser;
import fr.istic.taa.shared.User;

import javax.persistence.EntityTransaction;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Thomas & Amona on 23/10/14.
 */
@Path("/users")
public class UserResource implements IUserResource {

    private static ManagerSingleton manager = ManagerSingleton.getInstance();

    public UserResource() {}

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<IUser> getUsers() {
        return manager.createQuery("select u from User as u").getResultList();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public IUser getUserById(@PathParam("id") String id) {
        return manager.find(User.class, Integer.parseInt(id));
    }

    @GET
    @Path("/search/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public IUser getUserByName(@PathParam("name") String name) {
        List<IUser> users = (List<IUser>) manager.createQuery("select u from User as u where u.username = '" + name + "'").getResultList();

        if (users.isEmpty())
            return null;

        return users.get(0);
    }

    @POST
    @Path("/authenticate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Boolean> authenticate(User user) {
        Map<String, Boolean> errors = new HashMap<String, Boolean>();
        List<IUser> u = (List<IUser>) manager.createQuery("select u from User as u where u.password = '" + user.getPassword() + "'").getResultList();

        errors.put("username", getUserByName(user.getUsername()) != null);
        errors.put("password", u.size() != 0);

        return errors;
    }

    @POST
    @Path("/create/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public IUser create(User user) {
        EntityTransaction t = manager.getTransaction();

        t.begin();
        manager.persist(user);
        t.commit();

        return user;
    }

    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public IUser update(User update) {
        EntityTransaction t = manager.getTransaction();

        t.begin();
        manager.merge(update);
        t.commit();

        return update;
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public IUser deleteById(@PathParam("id") String id) {
        EntityTransaction t = manager.getTransaction();

        t.begin();
        User user = manager.find(User.class, Integer.parseInt(id));

        // Get rides and remove every passengers
        List<IRide> rides = user.getRidesAsDriver();

        for (IRide ride : rides) {
            for (IUser passenger : ride.getPassengers()) {
                passenger.removeRidesAsPassenger(ride);
            }
            manager.remove(ride);
        }

        // Finally, remove the user
        manager.remove(user);
        t.commit();

        return user;
    }
}
