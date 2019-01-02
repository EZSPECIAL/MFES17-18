package MFESTA.java;

import MFESTA.User;

public class ObjectHandler {

	/**
	 * Creates a User object using the specified initial balance.
	 * 
	 * @param balance the initial balance of the user
	 * @return the created user
	 */
	public User createUser(double balance) {
		return new User(balance);
	}
}
