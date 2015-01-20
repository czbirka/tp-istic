package fr.istic.chat.server;

import fr.istic.chat.client.IChatUser;

import javax.security.auth.login.FailedLoginException;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IChatRoom extends Remote {
    public void subscribe(IChatUser user) throws RemoteException;
    public void unsubscribe(String pseudo) throws RemoteException;

    public void postMessage(String pseudo, String message) throws RemoteException;
    public boolean authentification(String username, char[] password) throws FailedLoginException, RemoteException;
}