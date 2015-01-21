package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import play.libs.F;
import play.libs.Json;
import play.libs.ws.WSClient;
import services.models.Ride;
import services.models.User;

import java.util.List;


/**
 * JourneyService implementation that delegates to a third party JSON Web service
 */
public class RidesServiceHTTP implements IRidesService {

    /** The HTTP client used to communicate with the Web service */
    final WSClient client;

    /** A JSON object mapper to handle JSON serialization/deserialization */
    final ObjectMapper mapper;

    /** The Web service base URL */
    final static String API_URL = "http://localhost:8080/rest/rides";

    public RidesServiceHTTP(WSClient client) {
        this.client = client;
        mapper = new ObjectMapper();
    }

    @Override
    public F.Promise<List<Ride>> allRides() {
        return client.url(API_URL)
                .get()
                .map(r -> mapper.readValue(r.getBody(), new TypeReference<List<Ride>>() {}));
    }

    @Override
    public F.Promise<Ride> getRideById(Long id) {
       return client.url(API_URL + "/" + id)
                    .get()
                    .map(r -> {
                        Ride ride = new Ride();
                        try {
                            ride = mapper.readValue(r.getBody(), new TypeReference<Ride>() {});
                        } catch (Exception e) {
                            return null;
                        }
                        return ride;
                    });
    }

    @Override
    public F.Promise<Ride> createRide(Ride ride) {
        return client.url(API_URL + "/create")
                .post(Json.toJson(ride))
                .map(r -> mapper.readValue(r.getBody(), new TypeReference<Ride>() {
                }));
    }

    @Override
    public F.Promise<Ride> deleteById(Long id) {
        return client.url(API_URL + "/delete/" + id)
                .delete()
                .map(r -> mapper.readValue(r.getBody(), new TypeReference<Ride>() {
                }));
    }

    @Override
    public F.Promise<Ride> addPassenger(Long rideId, User passenger) {
        return client.url(API_URL + "/" + rideId + "/join")
                .put(Json.toJson(passenger))
                .map(r -> mapper.readValue(r.getBody(), new TypeReference<Ride>() {
                }));
    }
}
