package fr.istic.tp.spring.engine;

import fr.istic.tp.spring.command.ICommand;

public interface IEngine {

	public void setStartCommand(ICommand cmd);
	
	public void setStopCommand(ICommand cmd);
	
	public void run();
	
}
