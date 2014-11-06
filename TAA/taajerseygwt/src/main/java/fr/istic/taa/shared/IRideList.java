package fr.istic.taa.shared;

import java.util.List;

/**
 * Created by thomas on 05/11/14.
 */
public interface IRideList {
    void setRides(List<IRide> rides);
    List<IRide> getRides();
}
