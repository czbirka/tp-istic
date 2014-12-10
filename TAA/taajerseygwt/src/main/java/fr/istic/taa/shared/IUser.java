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
    public String getPassword();
    public void setPassword(final String password);

    public List<IRide> getRidesAsDriver();
    public void setRidesAsDriver(List<IRide> ridesAsDriver);
    public List<IRide> getRidesAsPassenger();

    public void addRidesAsDriver(IRide ride);
    public void removeRidesAsDriver(IRide ride);
    public void addRidesAsPassenger(IRide ride);
    public void removeRidesAsPassenger(IRide ride);
    public void setRidesAsPassenger(List<IRide> ridesAsPassenger);

}
