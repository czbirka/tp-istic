package fr.istic.taa.shared;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.sql.Date;
import java.util.Collection;

/**
 * Created by thomas on 06/10/14.
 */
public interface IRide {

    int getId();

    void setId(int id);

    String getOrigin();

    void setOrigin(String origin);

    String getDestination();

    void setDestination(String destination);

    Date getLeavingDate();

    void setLeavingDate(Date leavingDate);

    int getSeatNumber();

    void setSeatNumber(int seatNumber);

    Driver getDriver();

    void setDriver(Driver driver);

    Collection<Passenger> getPassengers();

    void setPassengers(Collection<Passenger> passengers);
}
