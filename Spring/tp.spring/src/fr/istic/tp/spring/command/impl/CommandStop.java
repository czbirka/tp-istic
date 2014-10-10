package fr.istic.tp.spring.command.impl;

import fr.istic.tp.spring.command.ICommand;

public class CommandStop implements ICommand {

	@Override
	public Boolean execute() {
		System.out.println("Hello from stop Command POC");
		return true;
	}

}
