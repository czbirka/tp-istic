package observer;

import java.util.ArrayList;

/**
 * Created by thomas & amona on 13/10/14.
 */
public class Observable {
    ArrayList<Observer> observers;

    public Observable() {
        observers = new ArrayList<Observer>();
    }

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(this);
        }
    }
}
