package fr.istic.taa.server;

import fr.istic.taa.shared.User;

import java.util.Collection;

/**
 * Created by Thomas & Amona on 23/10/14.
 */
public interface IUserResource {

    Collection<User> getUsers();

    User getUserById(String id);

    Collection<User> create(User ride);

    Collection<User> update(User update);

    Collection<User> deleteById(String id);
}
