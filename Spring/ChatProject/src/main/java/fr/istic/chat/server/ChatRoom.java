package fr.istic.chat.server;

import fr.istic.chat.client.IChatUser;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.security.auth.login.FailedLoginException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;


public class ChatRoom extends UnicastRemoteObject implements IChatRoom {
	private static final long serialVersionUID = 1L;
	private Hashtable<String, IChatUser> users;
	private Hashtable<String, char[]> alloweduser;

	// configurable option
	private boolean debug = false;

	public ChatRoom() throws RemoteException {
		super();

		users = new Hashtable<String, IChatUser>();
		alloweduser = new Hashtable<String, char[]>();
		alloweduser.put("toto", "passtoto".toCharArray());
		alloweduser.put("titi", "passtiti".toCharArray());
		alloweduser.put("testUser", "testPassword".toCharArray());
	}

	public void subscribe(IChatUser user) throws RemoteException {
		String message = "Connexion de " + user.getPseudo();
		this.postMessage("ChatRoom", message);
		System.out.println(message);
		this.users.put(user.getPseudo(), user);
	}

	public void unsubscribe(String pseudo) throws RemoteException {
		String message = "Deconnexion de " + pseudo;
		System.out.println(message);
		this.users.remove(pseudo);
		this.postMessage("ChatRoom", message);
	}

	public void postMessage(String pseudo, String message)
			throws RemoteException {
		String fullMessage = pseudo + " >> " + message;
		System.out.println(fullMessage);

		for (IChatUser user : users.values()) {
			user.displayMessage(fullMessage);
		}
	}

	public boolean authentification(String username, char[] password)
			throws FailedLoginException, RemoteException {
		// verify the username/password
		boolean usernameCorrect = false;
		boolean passwordCorrect = false;
		if (this.alloweduser.containsKey(username)) {
			usernameCorrect = true;
			if (password.length == this.alloweduser.get(username).length
					&& testPassword(this.alloweduser.get(username), password)) {

				// authentication succeeded!!!
				passwordCorrect = true;
				if (debug)
					System.out.println("\t\t[SampleLoginModule] "
							+ "authentication succeeded");
				return true;
			}

		}

		// authentication failed -- clean out state
		if (debug)
			System.out.println("\t\t[SampleLoginModule] "
					+ "authentication failed");
		for (int i = 0; i < password.length; i++)
			password[i] = ' ';
		if (!usernameCorrect) {
			throw new FailedLoginException("User Name Incorrect");
		} else {
			throw new FailedLoginException("Password Incorrect");
		}
	}

	private boolean testPassword(char[] cs, char[] password) {
		boolean result = true;
		int i = 0;
		while (i < cs.length && result) {
			if (cs[i] != password[i])
				result = false;
			i++;
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("server-beans.xml");
		System.out.println("ChatRoom: Waiting for requests");
	}
}