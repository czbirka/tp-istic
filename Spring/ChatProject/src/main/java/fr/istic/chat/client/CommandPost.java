package fr.istic.chat.client;

import java.rmi.RemoteException;

import fr.istic.chat.server.IChatRoom;


public class CommandPost implements Command {

	private IChatRoom room;
	private IChatUser user;
	private ChatUI ui;

	public CommandPost() {
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
	 * Gets the UI.
	 * @return
	 */
	public ChatUI getUi() {
		return ui;
	}

	/**
	 * Sets the UI.
	 * @param ui
	 */
	public void setUi(ChatUI ui) {
		this.ui = ui;
	}
	
	public void execute() {
		try {
			room.postMessage(user.getPseudo(), ui.getMessage());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
