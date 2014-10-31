package fr.istic.taa.shared;

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

    User getDriver();

    void setDriver(User driver);

    Collection<User> getPassengers();

    void setPassengers(Collection<User> passengers);
}
