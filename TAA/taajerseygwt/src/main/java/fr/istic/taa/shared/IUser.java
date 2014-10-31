package fr.istic.taa.shared;

import java.util.Collection;

/**
 * Created by Thomas & Amona on 06/10/14.
 */
public interface IUser {

    public int getId();
    public void setId(final int id);
    public String getUsername();
    public void setUsername(final String username);
    public Collection<Ride> getRidesAsDriver();
    public void setRidesAsDriver(Collection<Ride> ridesAsDriver);
    public Collection<Ride> getRidesAsPassenger();
    public void setRidesAsPassenger(Collection<Ride> ridesAsPassenger);
    public Collection<Number> getRidesAsDriverID();
    public Collection<Number> getRidesAsPassengerID();
}
