package services;

import play.libs.F;
import services.models.Ride;
import services.models.User;

import java.util.List;

public interface IRidesService {

    public F.Promise<List<Ride>> allRides();

    public F.Promise<Ride> getRideById(Long id);

    public F.Promise<Ride> createRide(Ride ride);

    public F.Promise<Ride> deleteById(Long id);

    public F.Promise<Ride> addPassenger(Long rideId, User passenger);
}
