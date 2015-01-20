package fr.istic.chat.client;

public interface Command {
	void execute();
	IChatUser getUser();
	void setUser(IChatUser u);
}
