package fr.istic.taa.server;

import fr.istic.taa.shared.IUser;
import fr.istic.taa.shared.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Thomas & Amona on 23/10/14.
 */
public interface IUserResource {

    List<IUser> getUsers();

    IUser getUserById(String id);

    IUser getUserByName(String id);

    Map<String, Boolean> authenticate(User user);

    IUser create(User user);

    IUser update(User update);

    IUser deleteById(String id);
}
