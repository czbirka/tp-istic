package services;

import play.libs.F;
import services.models.User;

import java.util.List;
import java.util.Map;

/**
 * Created by amona on 04/12/14.
 */
public interface IUsersService {

    public F.Promise<List<User>> allUsers();

    public F.Promise<User> getUserByName(String name);

    public F.Promise<User> getUserById(Long id);

    public F.Promise<Map<String, Boolean>> authenticate(User user);

    public F.Promise<User> createUser(User user);
}
