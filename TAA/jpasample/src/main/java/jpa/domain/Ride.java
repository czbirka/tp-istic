package jpa.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Ride implements Serializable, IRide {

	/**
	 * Id for serializable class
	 */
	private static final long serialVersionUID = 6864650714900218803L;

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
	private Driver driver;
	
	/**
	 * List of passengers of the ride
	 */
	private Collection<Passenger> passengers;

	/**
	 * Gets the id of the ride
	 * @return id
	 */
	@Override
    @Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	
	/**
	 * Sets the id of the ride
	 * @param id
	 */
	@Override
    public void setId(final int id) {
		this.id = id;
	}

	/**
	 * Gets the departure location
	 * @return origin
	 */
	@Override
    public String getOrigin() {
		return origin;
	}

	/**
	 * Sets the departure location
	 * @param origin
	 */
	@Override
    public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * Gets the arrival location
	 * @return destination
	 */
	@Override
    public String getDestination() {
		return destination;
	}

	/**
	 * Sets the arrival location
	 * @param destination
	 */
	@Override
    public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * Gets the departure date
	 * @return leavingDate
	 */
	@Override
    public Date getLeavingDate() {
		return leavingDate;
	}

	/**
	 * Sets the departure date
	 * @param leavingDate
	 */
	@Override
    public void setLeavingDate(Date leavingDate) {
		this.leavingDate = leavingDate;
	}
	
	/**
	 * Gets the number of seats for the ride
	 * @return seatNumber
	 */
	@Override
    public int getSeatNumber() {
		return seatNumber;
	}

	/**
	 * Sets the number of seats for the ride
	 * @param seatNumber
	 */
	@Override
    public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	/**
	 * Gets the driver of the ride
	 * @return driver
	 */
	@Override
    @ManyToOne
	public Driver getDriver() {
		return driver;
	}

	/**
	 * Sets the driver of the ride
	 * @param driver
	 */
	@Override
    public void setDriver(Driver driver) {
		this.driver = driver;
	}
	
	/**
	 * Gets the passengers of the ride
	 * @return passengers
	 */
	@Override
    @ManyToMany(mappedBy="rides")
	public Collection<Passenger> getPassengers() {
		return passengers;
	}

	/**
	 * Sets the passengers of the ride
	 * @param passengers
	 */
	@Override
    public void setPassengers(Collection<Passenger> passengers) {
		this.passengers = passengers;
	}

}
