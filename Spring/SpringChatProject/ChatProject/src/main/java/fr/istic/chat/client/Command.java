package fr.istic.chat.client;

public interface Command {
	void execute();
	void setUser(User u);
}
