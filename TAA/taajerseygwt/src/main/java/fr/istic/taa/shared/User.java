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
        this.ridesAsDriver = new ArrayList<IRide>();
        this.ridesAsPassenger = new ArrayList<IRide>();
    }

    @JsonCreator
    public User(@JsonProperty("username") String username) {
        this.username = username.length() > 0 ? username : "Username";
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
    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, targetEntity = Ride.class)
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

    public void addRidesAsDriver(IRide ride) {
        ridesAsDriver.add(ride);
    }

    public void removeRidesAsDriver(IRide ride) {
        ridesAsDriver.remove(ride);
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

    public void addRidesAsPassenger(IRide ride) {
        ridesAsPassenger.add(ride);
    }

    @Transient
    public Collection<Number> getRidesAsDriverID() {
        Collection<Number> ids = new HashSet<Number>();
        for (IRide ride : getRidesAsDriver()) {
            ids.add(ride.getId());
        }
        return ids;
    }

    @Transient
    public Collection<Number> getRidesAsPassengerID() {
        Collection<Number> ids = new HashSet<Number>();
        for (IRide ride : getRidesAsPassenger()) {
            ids.add(ride.getId());
        }
        return ids;
    }
}
