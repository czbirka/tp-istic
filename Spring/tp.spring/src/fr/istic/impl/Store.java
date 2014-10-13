package fr.istic.impl;

import fr.istic.interfaces.IBank;
import fr.istic.interfaces.IFastLane;
import fr.istic.interfaces.IJustHaveALook;
import fr.istic.interfaces.ILane;
import fr.istic.interfaces.IProvider;
import fr.istic.interfaces.IStore;

public class Store implements IStore, IJustHaveALook, ILane, IFastLane {
	private IProvider provider;
	private IBank bank;
	
	public IProvider getProvider() {
		return provider;
	}

	public void setProvider(IProvider provider) {
		this.provider = provider;
	}
	
	public IBank getBank() {
		return bank;
	}

	public void setBank(IBank bank) {
		this.bank = bank;
	}

	@Override
	public void oneShotOrder() {
		System.out.println("One shot order !");
	}

	@Override
	public void addItemToCart() {
		System.out.println("Item added to cart.");
	}

	@Override
	public void pay() {
		System.out.println("Payment accepted. Thank you !");
	}

	@Override
	public int getPrice() {
		return 0;
	}

	@Override
	public boolean isAvailable() {
		return true;
	}

	
}
