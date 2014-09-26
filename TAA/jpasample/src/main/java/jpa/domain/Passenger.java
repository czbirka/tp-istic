package jpa.domain;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Passenger implements Serializable {

	/**
	 * Id for serializable class
	 */
	private static final long serialVersionUID = 7931470498245035451L;
	
	/**
	 * The id of the passenger
	 */
	private int id;
	
	/**
	 * The list of rides a passenger can have
	 */
	private Collection<Ride> rides;

	/**
	 * Gets the id of the passenger
	 * @return id
	 */
	@Id
	public int getId() {
		return id;
	}

	/**
	 * Sets the id of the passenger
	 * @param id
	 */
	public void setId(final int id) {
		this.id = id;
	}

	/**
	 * Gets the rides of the passenger
	 * @return rides
	 */
	@ManyToMany
	public Collection<Ride> getRides() {
		return rides;
	}

	/**
	 * Sets the rides of the passenger
	 * @param rides
	 */
	public void setRides(Collection<Ride> rides) {
		this.rides = rides;
	}

}
