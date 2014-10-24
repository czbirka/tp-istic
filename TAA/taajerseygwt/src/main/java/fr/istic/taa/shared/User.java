package fr.istic.taa.shared;

import java.util.Collection;

/**
 * Created by Thomas & Amona on 06/10/14.
 */
public interface User {

    public int getId();
    public void setId(final int id);
    public String getUsername();
    public void setUsername(final String username);
    public Collection<Ride> getRides();
    public void setRides(Collection<Ride> rides);
}
