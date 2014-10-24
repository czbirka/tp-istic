package fr.istic.taa.server;

import fr.istic.taa.shared.Ride;

import javax.persistence.*;

/**
 * Created by Thomas & Amona on 16/10/14.
 */
public class ManagerSingleton {

    private ManagerSingleton() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("dev");
        manager = factory.createEntityManager();
    }

    private static ManagerSingleton instance = null;

    private EntityManager manager;

    public static ManagerSingleton getInstance() {

        if (instance == null)
            instance = new ManagerSingleton();

        return instance;
    }

    public void persist(Object o) {
        manager.persist(o);
    }

    public void remove(Object o) {
        manager.remove(o);
    }

    public EntityTransaction getTransaction() {
        return manager.getTransaction();
    }

    public Query createQuery(String query) {
        return manager.createQuery(query);
    }

    public <T> T merge(T entity) {
        return manager.merge(entity);
    }

    public <T> T find(Class<T> entityClass, Object primaryKey) {
        return manager.find(entityClass, primaryKey);
    }
}
