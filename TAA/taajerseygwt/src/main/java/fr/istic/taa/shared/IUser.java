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

    public List<IRide> getRidesAsDriver();
    public void setRidesAsDriver(List<IRide> ridesAsDriver);
    public List<IRide> getRidesAsPassenger();

    public void removeRidesAsDriver(IRide ride);
    public void addRidesAsDriver(IRide ride);
    public void addRidesAsPassenger(IRide ride);
    public void setRidesAsPassenger(List<IRide> ridesAsPassenger);

}
