package jpa;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import jpa.domain.Driver;
import jpa.domain.Passenger;
import jpa.domain.Ride;

public class JpaTest {

	private EntityManager manager;

	public JpaTest(EntityManager manager) {
		this.manager = manager;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("dev");
		EntityManager manager = factory.createEntityManager();
		JpaTest test = new JpaTest(manager);

		EntityTransaction tx = manager.getTransaction();
		tx.begin();

		try {
			Driver driver = new Driver();
			
			Collection<Ride> rides = new ArrayList<Ride>();
			Ride r = new Ride();
			r.setOrigin("Rennes");
			r.setDestination("Tahiti");
			r.setSeatNumber(2);
			r.setLeavingDate(new Date(new java.util.Date().getTime()));
			r.setDriver(driver);
			
			Collection<Passenger> ps = new ArrayList<Passenger>();
			Passenger p = new Passenger();
			p.setRides(rides);
			ps.add(p);
			
			r.setPassengers(ps);
			rides.add(r);
			driver.setRides(rides);
			
			// Persist the objects
			manager.persist(driver);
			manager.persist(r);
			manager.persist(p);
			
			String textQuery = "SELECT d FROM Driver as d";
			Query q = manager.createQuery(textQuery);
			
			Driver d2 = (Driver) q.getSingleResult();
			
			System.out.println("Saved driver id: " + d2.getId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		tx.commit();
		/*
		Enseignant es = (Enseignant) manager.createQuery(
				"select e1 from Enseignant as e1 where e1.nom='barais'")
				.getSingleResult();*/
		
	}

}
