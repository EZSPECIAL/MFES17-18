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
	
	/**
	 * Creates a Document object using the specified parameters.
	 * 
	 * @param name the document name
	 * @param pages the number of pages the document has
	 * @param format the paper format needed for the document
	 * @param toner the paper toner needed for the document
	 * @return the created document
	 */
	public Document createDocument(String name, int pages, Character format, Character toner) {
		return new Document(name, pages, format, toner);
	}
}
