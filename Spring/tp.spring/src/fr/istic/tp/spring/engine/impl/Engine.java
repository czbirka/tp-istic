package fr.istic.tp.spring.engine.impl;

import fr.istic.tp.spring.command.ICommand;
import fr.istic.tp.spring.engine.IEngine;

public class Engine implements IEngine {

	private ICommand startCommand=null;
	private ICommand stopCommand=null;

	/* AUTO GENERATED GETTER & SETTER */
	public ICommand getStartCommand() {
		return startCommand;
	}

	public ICommand getStopCommand() {
		return stopCommand;
	}

	public void setStopCommand(ICommand stopCommand) {
		this.stopCommand = stopCommand;
	}

	public void setStartCommand(ICommand startCommand) {
		this.startCommand = startCommand;
	}



	@Override
	public void run() {
		startCommand.execute();
		stopCommand.execute();
	}

}
