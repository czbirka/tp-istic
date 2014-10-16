package fr.istic.taa.shared;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Driver implements User, Serializable {

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
	private Collection<Ride> rides;

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
	@OneToMany(mappedBy="driver")
	public Collection<Ride> getRides() {
		return rides;
	}

	/**
	 * Sets the ride list of the driver
	 * @param rides
	 */
	public void setRides(Collection<Ride> rides) {
		this.rides = rides;
	}
}
