package jpa.server;

import jpa.domain.IRide;
import jpa.domain.Ride;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;
import java.util.Collection;

/**
 * Created by Thomas & Amona on 06/10/14.
 */
public interface IRideResource {

    Collection<Ride> getRides();

    Ride getRideById(String id);

    Collection<Ride> create(Ride ride);

    Collection<Ride> update(Ride update);

    Collection<Ride> deleteById(String id);
}
