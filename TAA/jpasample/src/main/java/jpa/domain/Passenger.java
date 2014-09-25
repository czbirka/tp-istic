package jpa.domain;

import java.io.Serializable;
import java.util.List;

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
	 * Id of the passenger
	 */
	private int id;
	
	/**
	 * 
	 */
	private List<Ride> rides;

	/**
	 * Gets the id of the passenger
	 * @return
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
	 * 
	 * @return
	 */
	@ManyToMany
	public List<Ride> getRides() {
		return rides;
	}

	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}

}
