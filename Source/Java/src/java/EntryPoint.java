package MFESTA.java;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.overture.codegen.runtime.VDMSet;

import MFESTA.Document;
import MFESTA.User;

public class EntryPoint {

	// Constants
	private static final int numColumns = 3;
	private static final String keyMsg = "Press Enter to continue...";

	// Helper classes
	private static MenuHandler menuHandler = new MenuHandler();
	private static ObjectHandler objectHandler = new ObjectHandler();
	
	// VDM Objects
	private static TreeMap<String, User> users = new TreeMap<String, User>();
	private static TreeMap<String, Document> documents = new TreeMap<String, Document>();

	public static void main(String[] args) {

		mainMenuLogic();
	}

	// TODO doc
	private static void mainMenuLogic() {

		while(true) {

			menuHandler.clrscr();
			int maxChoice = menuHandler.mainMenu();

			int userChoice = menuHandler.intRangeInput("Please input a number in range [1, " + maxChoice + "]", 1, maxChoice);

			switch(userChoice) {
			case 1:
				// User menu
				userMenuLogic();
				break;
			case 2:
				// Document menu
				documentMenuLogic();
				break;
			}
		}
	}

	// TODO doc
	private static void userMenuLogic() {

		boolean onMenu = true;

		while(onMenu) {

			menuHandler.clrscr();
			int maxChoice = menuHandler.userMenu();
			
			int intChoice = menuHandler.intRangeInput("Please input a number in range [1, " + maxChoice + "]", 1, maxChoice);

			switch(intChoice) {
			case 1:
				// Create user
				double initBalance = menuHandler.doubleGTEInput("Please input an initial balance for the user (must be >= 0.0)", 0);
				users.put("User" + users.size(), objectHandler.createUser(initBalance));
				break;
			case 2:
				// List users
				
				// Check users exist
				if(users.size() == 0) {
					System.out.println("No users created! Please create one before using this menu.");
				} else {
					menuHandler.prettyPrintUsers(users, numColumns);
				}
				menuHandler.waitOnKey(keyMsg);
				break;
			case 3:
				// List user documents
				
				int userChoice;
				// Check users exist
				if(users.size() == 0) {
					System.out.println("No users created! Please create one before using this menu.");
					menuHandler.waitOnKey(keyMsg);
					break;
				} else {
					menuHandler.prettyPrintUsers(users, numColumns);
					userChoice = menuHandler.intRangeInput("Please input a number in range [0, " + (users.size() - 1) + "]", 0, users.size() - 1);
				}
				
				VDMSet documentSet = users.get("User" + userChoice).getDocumentList();
				if(documentSet.size() == 0) {
					System.out.println("User selected has no documents! Please select another user.");
				} else {
					menuHandler.prettyPrintDocumentsSet(documents, users.get("User" + userChoice).getDocumentList(), numColumns);
				}
				menuHandler.waitOnKey(keyMsg);
				break;
			case 4:
				// Add document to user

				// Check users exist
				if(users.size() == 0) {
					System.out.println("No users created! Please create one before using this menu.");
					menuHandler.waitOnKey(keyMsg);
					break;
				} else {
					menuHandler.prettyPrintUsers(users, numColumns);
					userChoice = menuHandler.intRangeInput("Please input a number in range [0, " + (users.size() - 1) + "]", 0, users.size() - 1);
				}
				
				// Check documents exist
				if(documents.size() == 0) {
					System.out.println("No documents available to add! Please create one before using this menu.");
					menuHandler.waitOnKey(keyMsg);
					break;
				} else {
					
					// Find documents that aren't already in selected user's documents
					TreeMap<String, Document> userDocuments = new TreeMap<String, Document>();
					for(Map.Entry<String, Document> entry : documents.entrySet()) {
						
						if(!users.get("User" + userChoice).getDocumentList().contains(entry.getValue())) userDocuments.put(entry.getKey(), entry.getValue());
					}
					
					// Check if user already has all the available documents
					if(userDocuments.size() == 0) {
						System.out.println("User already has all the documents in its list! Cannot add.");
						menuHandler.waitOnKey(keyMsg);
						break;
					}
					
					menuHandler.prettyPrintDocuments(userDocuments, numColumns);
					
					// Await valid document input
					int docChoice;
					do {
						docChoice = menuHandler.intInput("Please input a number seen above.");
					} while(!userDocuments.containsKey("Doc" + docChoice));
					
					// Check if document name is already in user's documents
					boolean isInvalid = false;

					String selectedDocName = documents.get("Doc" + docChoice).getDocName();
					for(Iterator<Document> iter = users.get("User" + userChoice).getDocumentList().iterator(); iter.hasNext(); ) {
						Document doc = iter.next();
						if(doc.getDocName().equals(selectedDocName)) {
							isInvalid = true;
							break;
						}
					}

					// Cannot add documents with the same name to the same user
					if(isInvalid) {
						System.out.println("User already has a document with that name, cannot add this document.");
						menuHandler.waitOnKey(keyMsg);
						break;
					}
					users.get("User" + userChoice).addDocument(documents.get("Doc" + docChoice));
				}
				break;
			case 5:
				// Remove document from user
				
				// Check users exist
				if(users.size() == 0) {
					System.out.println("No users created! Please create one before using this menu.");
					menuHandler.waitOnKey(keyMsg);
					break;
				} else {
					menuHandler.prettyPrintUsers(users, numColumns);
					userChoice = menuHandler.intRangeInput("Please input a number in range [0, " + (users.size() - 1) + "]", 0, users.size() - 1);
				}
				
				documentSet = users.get("User" + userChoice).getDocumentList();
				
				// Check documents exist
				if(documentSet.size() == 0) {
					System.out.println("No documents available to remove! Please add documents to the user before removing.");
					menuHandler.waitOnKey(keyMsg);
					break;
				} else {
					
					// Find documents that are in the selected user's documents
					TreeMap<String, Document> userDocuments = new TreeMap<String, Document>();
					for(Map.Entry<String, Document> entry : documents.entrySet()) {
						
						if(users.get("User" + userChoice).getDocumentList().contains(entry.getValue())) userDocuments.put(entry.getKey(), entry.getValue());
					}
					
					menuHandler.prettyPrintDocuments(userDocuments, numColumns);
					
					// Await valid document input
					int docChoice;
					do {
						docChoice = menuHandler.intInput("Please input a number seen above.");
					} while(!userDocuments.containsKey("Doc" + docChoice));
	
					// Remove document from user's document list
					users.get("User" + userChoice).removeDocument(documents.get("Doc" + docChoice));
				}
				break;
			case 6:
				onMenu = false;
				break;
			}
		}
	}

	/**
	 * Handles document management menu options. 
	 */
	private static void documentMenuLogic() {

		boolean onMenu = true;

		while(onMenu) {

			menuHandler.clrscr();
			int maxChoice = menuHandler.documentMenu();

			int intChoice = menuHandler.intRangeInput("Please input a number in range [1, " + maxChoice + "]", 1, maxChoice);

			switch(intChoice) {
			case 1:
				// Create document
				createDocumentMenuLogic();
				break;
			case 2:
				// List documents
				
				// Check documents exist
				if(documents.size() == 0) {
					System.out.println("No documents created! Please create one before using this menu.");
				} else {
					menuHandler.prettyPrintDocuments(documents, numColumns);
				}
				menuHandler.waitOnKey(keyMsg);
				break;
			case 3:
				onMenu = false;
				break;
			}
		}
	}

	/**
	 * Handles document creation menu options. 
	 */
	private static void createDocumentMenuLogic() {

		boolean onMenu = true;

		while(onMenu) {

			menuHandler.clrscr();
			int maxChoice = menuHandler.createDocumentMenu();

			int intChoice = menuHandler.intRangeInput("Please input a number in range [1, " + maxChoice + "]", 1, maxChoice);

			// Check exit option
			if(intChoice == maxChoice) {
				onMenu = false;
			} else {

				int numPages = menuHandler.intGTEInput("Please input the number of pages the document has (must be > 0)", 1);
				String docName = menuHandler.inputString("Please input the document name (cannot be empty)");

				// Parse user selection and create document
				if(intChoice == 1) {
					documents.put("Doc" + documents.size(), objectHandler.createDocument(docName, numPages, '4', 'B'));
				} else if(intChoice == 2) {
					documents.put("Doc" + documents.size(), objectHandler.createDocument(docName, numPages, '4', 'C'));
				} else if(intChoice == 3) {
					documents.put("Doc" + documents.size(), objectHandler.createDocument(docName, numPages, '3', 'B'));
				} else if(intChoice == 4) {
					documents.put("Doc" + documents.size(), objectHandler.createDocument(docName, numPages, '3', 'C'));
				}

				onMenu = false;
			}
		}
	}
}
