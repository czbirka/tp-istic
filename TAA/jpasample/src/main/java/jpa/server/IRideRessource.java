package jpa.server;

import jpa.domain.IRide;
import jpa.domain.Ride;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;
import java.util.Collection;

/**
 * Created by thomas on 06/10/14.
 */
public interface IRideRessource {

    Collection<Ride> getRides();

    Ride getRideById(String id);

    Ride create(JAXBElement<Ride> ride);

    Ride update(JAXBElement<Ride> ride);

    IRide deleteById(String id);
}
