package fr.istic.chat.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.rmi.RemoteException;

public class Main {
	public static void main(String[] args) throws RemoteException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("client-beans.xml");

		IChatUser client = (ChatUser) context.getBean("chatUser");
		client.createIHM();
	}

}
