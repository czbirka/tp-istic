package fr.istic.chat.client;

import com.sun.security.auth.callback.DialogCallbackHandler;

import fr.istic.chat.server.IChatRoom;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class ChatUser extends UnicastRemoteObject implements IChatUser, Runnable {

	private String pseudo;
	private IChatRoom room;
	private ChatUI ui;

	public ChatUser() throws RemoteException {
		super(); // Appel au constructeur de UnicastRemoteObject
	}

	/**
	 * Creates the HCI.
	 */
	public void createIHM() {
		ui.window_create();
		run();
	}

	/**
	 * Displays a message using the UI.
	 */
	public void displayMessage(String message) throws RemoteException {
		ui.displayMessage(message);
	}

	/**
	 * Run method.
	 */
	public void run() {
		try {
			SampleLoginModule lc = null;
			try {
				lc = new SampleLoginModule(room);
				lc.initialize(new Subject(), new DialogCallbackHandler(), null,
						new HashMap());

			} catch (SecurityException se) {
				System.err.println("Cannot create LoginContext. "
						+ se.getMessage());
				System.exit(-1);
			}

			// the user has 3 attempts to authenticate successfully
			int i;
			for (i = 0; i < 3; i++) {
				try {

					// attempt authentication
					lc.login();

					// if we return with no exception, authentication succeeded
					break;

				} catch (LoginException le) {

					System.err.println("Authentication failed:");
					System.err.println("  " + le.getMessage());
					try {
						Thread.currentThread().sleep(3000);
					} catch (Exception e) {
						// ignore
					}

				}
			}

			// did they fail three times?
			if (i == 3) {
				System.out.println("Sorry");

				System.exit(-1);
			}

			System.out.println("Authentication succeeded!");
			this.pseudo = ui.requestPseudo();
			this.room.subscribe(this);

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the 'pseudo'.
	 */
	public String getPseudo() {
		return pseudo;
	}

	/**
	 * Gets the room.
	 */
	public IChatRoom getRoom() {
		return room;
	}

	/**
	 * Sets the room.
	 */
	public void setRoom(IChatRoom room) {
		this.room = room;		
	}

	/**
	 * Gets the UI.
	 */
	public ChatUI getUi() {
		return ui;
	}

	/**
	 * Sets the UI.
	 */
	public void setUi(ChatUI ui) {
		this.ui = ui;
	}
}