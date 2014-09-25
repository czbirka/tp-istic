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
	 * 
	 */
	@OneToMany(mappedBy="ride")
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
	 * Sets the id of the passenger
	 * @param id
	 */
	public void setId(final int id) {
		this.id = id;
	}
	
}
