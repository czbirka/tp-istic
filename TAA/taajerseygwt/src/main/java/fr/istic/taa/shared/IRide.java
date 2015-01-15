package fr.istic.taa.shared;

import java.util.Date;
import java.util.List;

/**
 * Created by thomas & amona on 06/10/14.
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

    IUser getDriver();

    void setDriver(IUser driver);

    List<IUser> getPassengers();

    void setPassengers(List<IUser> passengers);

    void addPassenger(IUser passenger);
}
