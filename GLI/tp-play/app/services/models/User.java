package services.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import controllers.Journeys;
import controllers.UsersCtrl;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amona on 04/12/14.
 */
@JsonAutoDetect
public class User {

    @JsonIgnore
    public String action;

    @JsonProperty
    public Long id;

    @JsonProperty
    @Constraints.Required
    public String username;

    @JsonProperty
    @Constraints.Required
    public String password;

    @JsonProperty
    public List<Long> ridesAsDriverID;

    @JsonProperty
    public List<Long> ridesAsPassengerID;

    public User() {

    }

    public List<ValidationError> validate() {

        final List<ValidationError> errors = new ArrayList<>();


        if (username.trim().equals("")) {
            errors.add(new ValidationError("username", "Invalid username."));
        }

        if (password.trim().equals("")) {
            errors.add(new ValidationError("password", "Invalid password."));
        }

        if (ridesAsDriverID == null) {
            ridesAsDriverID = new ArrayList<>();
        }

        if (ridesAsPassengerID == null) {
            ridesAsPassengerID = new ArrayList<>();
        }

        UsersCtrl.authenticate(this).map(result -> {
            switch (action) {
                case "login":
                    if (!result.get("username")) {
                        errors.add(new ValidationError("username", "Unknown username."));
                    }

                    if (!result.get("password")) {
                        errors.add(new ValidationError("password", "Wrong password."));
                    }
                    break;
                case "register":
                    if (result.get("username")) {
                        errors.add(new ValidationError("username", "Username already used."));
                    }
                    break;
            }
            return result;
        }).get(UsersCtrl.DEFAULT_TIMEOUT);

        return errors.isEmpty() ? null : errors;
    }

    @JsonIgnore
    public List<Ride> getRidesAsDriver() {
        List<Ride> rides = new ArrayList<>();

        for (Long id : ridesAsDriverID) {
            rides.add(Journeys.getRideById(id));
        }

        return rides;
    }

    @JsonIgnore
    public List<Ride> getRidesAsPassenger() {
        List<Ride> rides = new ArrayList<>();

        for (Long id : ridesAsPassengerID) {
            rides.add(Journeys.getRideById(id));
        }

        return rides;
    }
}
