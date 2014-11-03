package fr.istic.taa.shared;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.*;

@Entity
public class User implements IUser, Serializable {

	/**
	 * Id for serializable class
	 */
	private static final long serialVersionUID = -5985613221089436758L;
	
	/**
	 * The id of the driver
	 */
	private int id;

    /**
     * The username of the driver
     */
    private String username;

    /**
     * The list of rides a user can have as a Driver
     */
    private Collection<Ride> ridesAsDriver;

    /**
     * The list of rides a user can have as a Passenger
     */
    private Collection<Ride> ridesAsPassenger;

	/**
	 * Gets the id of the driver
	 * @return id
	 */
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	
	/**
	 * Sets the id of the driver
	 * @param id
	 */
	public void setId(final int id) {
		this.id = id;
	}

    /**
     * Gets the driver's username
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the driver's username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the rides of the user as a Driver
     * @return ridesAsDriver
     */
    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
    @JsonIgnore
    public Collection<Ride> getRidesAsDriver() {
        if (ridesAsDriver == null)
            ridesAsDriver = new ArrayList<Ride>();
        return ridesAsDriver;
    }

    /**
     * Sets the rides of the user as a Driver
     * @param ridesAsDriver
     */
    public void setRidesAsDriver(Collection<Ride> ridesAsDriver) {
        this.ridesAsDriver = ridesAsDriver;
    }

    public void addRidesAsDriver(Ride ride) {
        ridesAsDriver.add(ride);
    }

    public void removeRidesAsDriver(Ride ride) {
        ridesAsDriver.remove(ride);
    }

    /**
     * Gets the rides of the user as a Passenger
     * @return ridesAsPassenger
     */
    @ManyToMany
    @JsonIgnore
    public Collection<Ride> getRidesAsPassenger() {
        if (ridesAsPassenger == null)
            ridesAsPassenger = new ArrayList<Ride>();
        return ridesAsPassenger;
    }

    /**
     * Sets the rides of the user as a Passenger
     * @param ridesAsPassenger
     */
    public void setRidesAsPassenger(Collection<Ride> ridesAsPassenger) {
        this.ridesAsPassenger = ridesAsPassenger;
    }

    public void addRidesAsPassenger(Ride ride) {
        ridesAsPassenger.add(ride);
    }

    @Transient
    public Collection<Number> getRidesAsDriverID() {
        Collection<Number> ids = new HashSet<Number>();
        for (Ride ride : getRidesAsDriver()) {
            ids.add(ride.getId());
        }
        return ids;
    }

    @Transient
    public Collection<Number> getRidesAsPassengerID() {
        Collection<Number> ids = new HashSet<Number>();
        for (Ride ride : getRidesAsPassenger()) {
            ids.add(ride.getId());
        }
        return ids;
    }
}
