package fr.istic.taa.shared;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Entity
public class Ride implements IRide, Serializable {

    /**
     * Id for serializable class
     */
    private static final long serialVersionUID = 7931470498245035451L;

	/**
	 * Id of the ride
	 */
	private int id;
	
	/**
	 * City name of departure
	 */
	private String origin;
	
	/**
	 * City name of arrival
	 */
	private String destination;
	
	/**
	 * Date of departure
	 */
	private Date leavingDate;
	
	/**
	 * Number of seats available for the ride
	 */
	private int seatNumber;

	/**
	 * Driver of the ride
	 */
	private User driver;
	
	/**
	 * List of passengers of the ride
	 */
	private List<User> passengers;

	/**
	 * Gets the id of the ride
	 * @return id
	 */
    @Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	
	/**
	 * Sets the id of the ride
	 * @param id
	 */
    public void setId(final int id) {
		this.id = id;
	}

	/**
	 * Gets the departure location
	 * @return origin
	 */
    public String getOrigin() {
		return origin;
	}

	/**
	 * Sets the departure location
	 * @param origin
	 */
    public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * Gets the arrival location
	 * @return destination
	 */
    public String getDestination() {
		return destination;
	}

	/**
	 * Sets the arrival location
	 * @param destination
	 */
    public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * Gets the departure date
	 * @return leavingDate
	 */
    public Date getLeavingDate() {
		return leavingDate;
	}

	/**
	 * Sets the departure date
	 * @param leavingDate
	 */
    public void setLeavingDate(Date leavingDate) {
		this.leavingDate = leavingDate;
	}
	
	/**
	 * Gets the number of seats for the ride
	 * @return seatNumber
	 */
    public int getSeatNumber() {
		return seatNumber;
	}

	/**
	 * Sets the number of seats for the ride
	 * @param seatNumber
	 */
    public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	/**
	 * Gets the driver of the ride
	 * @return driver
	 */
    @ManyToOne
	public User getDriver() {
		return driver;
	}

	/**
	 * Sets the driver of the ride
	 * @param driver
	 */
    public void setDriver(User driver) {
		this.driver = driver;
	}
	
	/**
	 * Gets the passengers of the ride
	 * @return passengers
	 */
    @ManyToMany(mappedBy="ridesAsPassenger")
	public List<User> getPassengers() {
        if (passengers == null)
            passengers = new ArrayList<User>();
		return passengers;
	}

	/**
	 * Sets the passengers of the ride
	 * @param passengers
	 */
    public void setPassengers(List<User> passengers) {
		this.passengers = passengers;
	}

}
