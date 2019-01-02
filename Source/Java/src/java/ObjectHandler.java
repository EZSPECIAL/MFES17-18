package MFESTA.java;

import MFESTA.User;

public class ObjectHandler {

	public User createUser(double balance) {
		return new User(balance);
	}
}
