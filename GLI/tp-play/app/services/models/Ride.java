package services.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import controllers.Authentication;
import controllers.Journeys;
import controllers.UsersCtrl;
import org.omg.CORBA.TIMEOUT;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;
import scala.concurrent.stm.CommitBarrier;
import services.JourneysService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * Created by amona on 04/12/14.
 */
@JsonAutoDetect
public class Ride {

    @JsonProperty
    public Long id;

    @JsonProperty
    @Constraints.Required
    public String origin;

    @JsonProperty
    @Constraints.Required
    public String destination;

    @JsonProperty
    @Constraints.Required
    public Date leavingDate;

    @JsonProperty
    @Constraints.Required
    public Integer seatNumber;

    @JsonProperty
    public User driver;

    @JsonProperty
    public List<User> passengers;

    public Ride() {

    }

    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<>();

        if (seatNumber < 1) {
            errors.add(new ValidationError("seatNumber", "Please enter a strictly positive number."));
        }

        if (passengers == null)
            passengers = new ArrayList<>();

        if (driver == null)
            UsersCtrl.getUserByName(Authentication.username()).map(u -> driver = u).get(UsersCtrl.DEFAULT_TIMEOUT);

        return errors.isEmpty() ? null : errors;
    }
}
