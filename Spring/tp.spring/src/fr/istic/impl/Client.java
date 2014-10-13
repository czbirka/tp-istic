package fr.istic.impl;

import fr.istic.interfaces.IClient;
import fr.istic.interfaces.IFastLane;
import fr.istic.interfaces.IJustHaveALook;
import fr.istic.interfaces.ILane;
import fr.istic.interfaces.IRun;

public class Client implements IClient, IRun {
	
	private IJustHaveALook have;
	private ILane lane;
	private IFastLane ilane;
	
	public IFastLane getIlane() {
		return ilane;
	}

	public void setIlane(IFastLane ilane) {
		this.ilane = ilane;
	}
	
	public IJustHaveALook getHave() {
		return have;
	}

	public void setHave(IJustHaveALook have) {
		this.have = have;
	}
	
	public ILane getLane() {
		return lane;
	}

	public void setLane(ILane lane) {
		this.lane = lane;
	}

	@Override
	public void run() {
		ilane.oneShotOrder();
		lane.addItemToCart();
		lane.pay();
	}

}
