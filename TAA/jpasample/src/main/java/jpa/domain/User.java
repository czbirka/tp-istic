package jpa.domain;

import java.util.Collection;

/**
 * Created by Thomas & Amona on 06/10/14.
 */
public interface User {

    public int getId();
    public void setId(final int id);
    public Collection<Ride> getRides();
    public void setRides(Collection<Ride> rides);
}
