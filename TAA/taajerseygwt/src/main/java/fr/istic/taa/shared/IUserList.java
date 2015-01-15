package fr.istic.taa.shared;

import java.util.List;

/**
 * Created by thomas & amona on 05/11/14.
 */
public interface IUserList {
    void setUsers(List<IUser> users);
    List<IUser> getUsers();
}
