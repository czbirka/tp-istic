package fr.istic.taa.shared;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

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
     * The password of the driver
     */
    private String password;

    /**
     * The list of rides a user can have as a Driver
     */
    private List<IRide> ridesAsDriver;

    /**
     * The list of rides a user can have as a Passenger
     */
    private List<IRide> ridesAsPassenger;

    @JsonCreator
    public User() {
        this.username = "Username";
        this.password = "password";
        this.ridesAsDriver = new ArrayList<IRide>();
        this.ridesAsPassenger = new ArrayList<IRide>();
    }

    @JsonCreator
    public User(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username.length() > 0 ? username : "Username";
        this.password = password.length() > 0 ? password : "password";
        this.ridesAsDriver = new ArrayList<IRide>();
        this.ridesAsPassenger = new ArrayList<IRide>();
    }

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
     * Sets the user's username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the user's password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the rides of the user as a Driver
     * @return ridesAsDriver
     */
    @OneToMany(
        mappedBy = "driver",
        orphanRemoval = true,
        targetEntity = Ride.class
    )
    @JsonIgnore
    public List<IRide> getRidesAsDriver() {
        if (ridesAsDriver == null)
            ridesAsDriver = new ArrayList<IRide>();
        return ridesAsDriver;
    }

    /**
     * Sets the rides of the user as a Driver
     * @param ridesAsDriver
     */
    public void setRidesAsDriver(List<IRide> ridesAsDriver) {
        this.ridesAsDriver = ridesAsDriver;
    }

    /**
     * Gets the rides of the user as a Passenger
     * @return ridesAsPassenger
     */
    @ManyToMany(targetEntity = Ride.class)
    @JsonIgnore
    public List<IRide> getRidesAsPassenger() {
        if (ridesAsPassenger == null)
            ridesAsPassenger = new ArrayList<IRide>();
        return ridesAsPassenger;
    }

    /**
     * Sets the rides of the user as a Passenger
     * @param ridesAsPassenger
     */
    public void setRidesAsPassenger(List<IRide> ridesAsPassenger) {
        this.ridesAsPassenger = ridesAsPassenger;
    }

    /**
     * Adds a ride in the ridesAsDriver list
     * @param ride
     */
    public void addRidesAsDriver(IRide ride) {
        ridesAsDriver.add(ride);
    }

    /**
     * Removes a ride in the ridesAsDriver list
     * @param ride
     */
    public void removeRidesAsDriver(IRide ride) {
        ridesAsDriver.remove(ride);
    }

    /**
     * Adds a ride in the ridesAsPassenger list
     * @param ride
     */
    public void addRidesAsPassenger(IRide ride) {
        ridesAsPassenger.add(ride);
    }

    /**
     * Removes a ride in the ridesAsPassenger list
     * @param ride
     */
    public void removeRidesAsPassenger(IRide ride) {
        ridesAsPassenger.remove(ride);
    }

    /**
     * Gets the ids of the rides where the user is a driver
     * @return the list of ids
     */
    @Transient
    public Collection<Number> getRidesAsDriverID() {
        Collection<Number> ids = new HashSet<Number>();
        for (IRide ride : getRidesAsDriver()) {
            ids.add(ride.getId());
        }
        return ids;
    }

    /**
     * Gets the ids of the rides where the user is a passenger
     * @return the list of ids
     */
    @Transient
    public Collection<Number> getRidesAsPassengerID() {
        Collection<Number> ids = new HashSet<Number>();
        for (IRide ride : getRidesAsPassenger()) {
            ids.add(ride.getId());
        }
        return ids;
    }
}
