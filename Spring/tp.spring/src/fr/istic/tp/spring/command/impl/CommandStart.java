package fr.istic.tp.spring.command.impl;

import fr.istic.tp.spring.command.ICommand;

public class CommandStart implements ICommand {

	@Override
	public Boolean execute() {
		System.out.println("Hello from start Command POC");
		return true;
	}

}
