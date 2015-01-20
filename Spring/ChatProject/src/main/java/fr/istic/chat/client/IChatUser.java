package fr.istic.chat.client;

import java.rmi.RemoteException;

import fr.istic.chat.server.IChatRoom;

public interface IChatUser extends User {
	public IChatRoom getRoom();
	public void setRoom(IChatRoom room);

	public ChatUI getUi();
	public void setUi(ChatUI ui);

	public void createIHM();
	public void displayMessage(String message) throws RemoteException;
}