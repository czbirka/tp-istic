package fr.istic.chat.client;

import java.rmi.RemoteException;

import fr.istic.chat.server.IChatRoom;


public class CommandUnregister implements Command {

	private IChatRoom room;
	private IChatUser user;

	public CommandUnregister() {

	}

	/**
	 * Gets the room.
	 * @return
	 */
	public IChatRoom getRoom() {
		return room;
	}

	/**
	 * Sets the room.
	 * @param room
	 */
	public void setRoom(IChatRoom room) {
		this.room = room;
	}

	/**
	 * Gets the user.
	 */
	public IChatUser getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 */
	public void setUser(IChatUser user) {
		this.user = user;
	}

	/**
	 * 
	 */
	public void execute() {
		try {
			room.unsubscribe(user.getPseudo());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
