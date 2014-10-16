package fr.istic.chat.client;

import java.rmi.RemoteException;

import fr.istic.chat.server.ChatRoom;


public class CommandUnregister implements Command {

	ChatRoom room = null;
	User user = null;
	
	public CommandUnregister(ChatRoom room) {
		this.room = room;
	}

	public void execute() {
		try {
			room.unsubscribe(user.getPseudo());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setUser(User user) {
		this.user = user;
	}

}
