package jpa.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

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
	 * Gets the id of the driver
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
	
}
