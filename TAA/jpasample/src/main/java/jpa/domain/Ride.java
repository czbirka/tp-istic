package jpa.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Ride implements Serializable {

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

	@Id
	public int getId() {
		return id;
	}
	
	public void setId(final int id) {
		this.id = id;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Date getLeavingDate() {
		return leavingDate;
	}

	public void setLeavingDate(Date leavingDate) {
		this.leavingDate = leavingDate;
	}
	
	public int getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}
}
