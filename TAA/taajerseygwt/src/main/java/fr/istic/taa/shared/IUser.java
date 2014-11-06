package fr.istic.taa.shared;

import java.util.List;

/**
 * Created by Thomas & Amona on 06/10/14.
 */
public interface IUser {

    public int getId();
    public void setId(final int id);
    public String getUsername();
    public void setUsername(final String username);
    public List<Ride> getRidesAsDriver();
    public void setRidesAsDriver(List<Ride> ridesAsDriver);
    public List<Ride> getRidesAsPassenger();
    /*
    public void removeRidesAsDriver(IRide ride);
    public void addRidesAsDriver(IRide ride);
    public void addRidesAsPassenger(IRide ride);
    public void setRidesAsPassenger(Collection<IRide> ridesAsPassenger);
    */
}
