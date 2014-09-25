package jpa.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Driver implements Serializable {

	/**
	 * Id for serializable class
	 */
	private static final long serialVersionUID = -5985613221089436758L;
	
	/**
	 * The id of the driver
	 */
	private int id;
	
	/**
	 * The list of rides a driver can have
	 */
	private List<Ride> rides;

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
	 * Gets the ride list of the driver
	 * @return rides
	 */
	@OneToMany
	public List<Ride> getRides() {
		return rides;
	}

	/**
	 * Sets the ride list of the driver
	 * @param rides
	 */
	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}
}
