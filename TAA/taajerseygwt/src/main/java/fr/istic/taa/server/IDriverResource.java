package fr.istic.taa.server;

import fr.istic.taa.shared.Driver;

import java.util.Collection;

/**
 * Created by Thomas & Amona on 23/10/14.
 */
public interface IDriverResource {

    Collection<Driver> getDrivers();

    Driver getRideById(String id);

    Collection<Driver> create(Driver ride);

    Collection<Driver> update(Driver update);

    Collection<Driver> deleteById(String id);
}
