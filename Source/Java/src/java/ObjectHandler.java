package MFESTA.java;

import MFESTA.Document;
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
	
	public Document createDocument(String name, int pages, Character format, Character toner) {
		return new Document(name, pages, format, toner);
	}
}
