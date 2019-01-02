package MFESTA.java;

import java.util.HashMap;

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
	private static HashMap<String, User> users = new HashMap<String, User>();
	private static HashMap<String, Document> documents = new HashMap<String, Document>();

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
				if(users.size() == 0) {
					System.out.println("No users created! Please create one before using this menu.");
				} else {
					menuHandler.prettyPrintUsers(users, numColumns);
				}
				menuHandler.waitOnKey(keyMsg);
				break;
			case 3:
				// List user documents TODO complete
				
				int userChoice = 0;
				if(users.size() == 0) {
					System.out.println("No users created! Please create one before using this menu.");
					menuHandler.waitOnKey(keyMsg);
					break;
				} else {
					menuHandler.prettyPrintUsers(users, numColumns);
					userChoice = menuHandler.intRangeInput("Please input a number in range [0, " + (users.size() - 1) + "]", 0, users.size() - 1);
				}
				
				System.out.println(users.get("User" + userChoice).getDocumentList());
				menuHandler.waitOnKey(keyMsg);
				break;
			case 4:
				// Add document to user
				
				userChoice = 0;
				if(users.size() == 0) {
					System.out.println("No users created! Please create one before using this menu.");
					menuHandler.waitOnKey(keyMsg);
					break;
				} else {
					menuHandler.prettyPrintUsers(users, numColumns);
					userChoice = menuHandler.intRangeInput("Please input a number in range [0, " + (users.size() - 1) + "]", 0, users.size() - 1);
				}
				
				if(documents.size() == 0) {
					System.out.println("No documents available to add! Please create one before using this menu.");
					menuHandler.waitOnKey(keyMsg);
					break;
				} else {
					menuHandler.prettyPrintDocuments(documents, numColumns);
					int docChoice = menuHandler.intRangeInput("Please input a number in range [0, " + (documents.size() - 1) + "]", 0, documents.size() - 1);
					users.get("User" + userChoice).addDocument(documents.get("Doc" + docChoice));
				}
				break;
			case 5:
				// Remove document from user
				break;
			case 6:
				onMenu = false;
				break;
			}
		}
	}

	// TODO doc
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

	// TODO doc
	private static void createDocumentMenuLogic() {

		boolean onMenu = true;

		while(onMenu) {

			menuHandler.clrscr();
			int maxChoice = menuHandler.createDocumentMenu();

			int intChoice = menuHandler.intRangeInput("Please input a number in range [1, " + maxChoice + "]", 1, maxChoice);

			if(intChoice == 5) {
				onMenu = false;
			} else {

				int numPages = menuHandler.intGTEInput("Please input the number of pages the document has (must be > 0)", 1);
				String docName = menuHandler.inputString("Please input the document name (cannot be empty)");

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
