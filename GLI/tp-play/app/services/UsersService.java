package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import play.libs.F;
import play.libs.Json;
import play.libs.ws.WSClient;
import services.models.User;

import java.util.List;
import java.util.Map;


public class UsersService implements IUsersService {

    /** The HTTP client used to communicate with the Web service */
    final WSClient client;

    /** A JSON object mapper to handle JSON serialization/deserialization */
    final ObjectMapper mapper;

    /** The Web service base URL */
    final static String API_URL = "http://localhost:8080/rest/users";

    public UsersService(WSClient client) {
        this.client = client;
        mapper = new ObjectMapper();
    }

    @Override
    public F.Promise<User> getUserByName(String name) {
        return client.url(API_URL + "/search/" + name)
                .get()
                .map(r -> mapper.readValue(r.getBody(), new TypeReference<User>() {}));
    }

    @Override
    public F.Promise<User> getUserById(Long id) {
        return client.url(API_URL + "/" + id)
                .get()
                .map(r -> mapper.readValue(r.getBody(), new TypeReference<User>() {}));
    }

    @Override
    public F.Promise<Map<String, Boolean>> authenticate(User user) {
        return client.url(API_URL + "/authenticate")
                .post(Json.toJson(user))
                .map(r -> mapper.readValue(r.getBody(), new TypeReference<Map<String, Boolean>>() {}));
    }

    @Override
    public F.Promise<List<User>> allUsers() {
        return client.url(API_URL)
                .get()
                .map(r -> mapper.readValue(r.getBody(), new TypeReference<List<User>>() {}));
    }

    @Override
    public F.Promise<User> createUser(User user) {

        return client.url(API_URL + "/create")
                .post(Json.toJson(user))
                .map(r -> mapper.readValue(r.getBody(), new TypeReference<User>() {}));
    }
}
