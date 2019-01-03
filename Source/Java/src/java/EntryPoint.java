package MFESTA.java;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.overture.codegen.runtime.VDMSet;

import MFESTA.Document;
import MFESTA.Printer;
import MFESTA.PrinterCapability;
import MFESTA.PrinterCapacity;
import MFESTA.PrinterPricing;
import MFESTA.User;

public class EntryPoint {

	// Constants
	private static final int canPrintA4 = 0;
	private static final int canPrintA3 = 1;
	private static final int canPrintBlack = 2;
	private static final int canPrintColor = 3;
	private static final int numColumns = 3;
	private static final String keyMsg = "Press Enter to continue...";

	// Helper classes
	private static MenuHandler menuHandler = new MenuHandler();
	private static ObjectHandler objectHandler = new ObjectHandler();
	
	// VDM Objects
	private static TreeMap<String, User> users = new TreeMap<String, User>();
	private static TreeMap<String, Document> documents = new TreeMap<String, Document>();
	private static TreeMap<String, Printer> printers = new TreeMap<String, Printer>();

	public static void main(String[] args) {

		if(args.length > 0) createTestData();
		mainMenuLogic();
	}

	/**
	 * Handles main menu options.
	 */
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
			case 3:
				// Printer menu
				printerMenuLogic();
				break;
			}
		}
	}

	// TODO doc
	private static void printerMenuLogic() {
		
		boolean onMenu = true;

		while(onMenu) {

			menuHandler.clrscr();
			int maxChoice = menuHandler.printerMenu();

			int intChoice = menuHandler.intRangeInput("Please input a number in range [1, " + maxChoice + "]", 1, maxChoice);

			switch(intChoice) {
			case 1:
				// Create printer
				createPrinterMenuLogic();
				break;
			case 2:
				// List printers
				
				// Check printers exist
				if(printers.size() == 0) {
					System.out.println("No printers created! Please create one before using this menu.");
				} else {
					menuHandler.prettyPrintPrinters(printers, 1);
				}
				menuHandler.waitOnKey(keyMsg);
				break;
			case 3:
				// Print document
				printDocumentMenuLogic();
				break;
			case 7:
				onMenu = false;
				break;
			}
		}
	}
	
	// TODO doc
	private static void printDocumentMenuLogic() {

		// Check users exist
		if(users.size() == 0) {
			System.out.println("No users created! Please create one before using this menu.");
			menuHandler.waitOnKey(keyMsg);
			return;
		}

		menuHandler.prettyPrintUsers(users, numColumns);
		int userChoice = menuHandler.intRangeInput("Please input a number in range [0, " + (users.size() - 1) + "]", 0, users.size() - 1);

		VDMSet documentSet = users.get("User" + userChoice).getDocumentList();

		// Check documents exist
		if(documentSet.size() == 0) {
			System.out.println("No documents available to print! Please add documents to the user before printing.");
			menuHandler.waitOnKey(keyMsg);
			return;
		} 

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

		Document toPrint = documents.get("Doc" + docChoice);

	}
	
	// TODO doc
	private static TreeMap<String, Printer> checkPrinterAvailability(Document doc) {
		
		int pagesLeft = (int) doc.getPagesLeft();
		char format = doc.getPageFormat();
		char toner = doc.getPageToner();
		
		boolean atLeastOnePrinterCapability = false;
		
		for(Map.Entry<String, Printer> entry : printers.entrySet()) {
			
			Printer printer = entry.getValue();
			
			boolean neededCapabilityExists = false;
			
			// Check printer for needed capabilities (paper/toner)
			if(format == '4' && toner == 'B') {
				neededCapabilityExists = printer.getPrinterCapabilities().getCanPrintA4() && printer.getPrinterCapabilities().getCanPrintBlack();
			} else if(format == '4' && toner == 'C') {
				neededCapabilityExists = printer.getPrinterCapabilities().getCanPrintA4() && printer.getPrinterCapabilities().getCanPrintColor();
			} else if(format == '3' && toner == 'B') {
				neededCapabilityExists = printer.getPrinterCapabilities().getCanPrintA3() && printer.getPrinterCapabilities().getCanPrintBlack();
			} else if(format == '3' && toner == 'C') {
				neededCapabilityExists = printer.getPrinterCapabilities().getCanPrintA3() && printer.getPrinterCapabilities().getCanPrintColor();
			}
			
			setBooleanTrueOnce(atLeastOnePrinterCapability, neededCapabilityExists);
			
			// TODO create temp map with capable printers
		}
		
		// Check temp map
		// Check printer has paper/toner left TODO
		// Check printer is operational TODO
	}
	
	// TODO doc
	private static boolean setBooleanTrueOnce(boolean toSet, boolean shouldSet) {
		
		if(toSet) return true;
		else if(shouldSet) return true;
		else return false;
	}
	
	// TODO doc
	private static void createPrinterMenuLogic() {

		menuHandler.clrscr();

		// Printer name
		String printerName = menuHandler.inputString("Please input the printer name (cannot be empty)");

		// Printer paper format
		System.out.println("Select printer paper format capabilities");
		int maxChoice = menuHandler.printerFormatMenu();
		int formatChoice = menuHandler.intRangeInput("Please input a number in range [1, " + maxChoice + "]", 1, maxChoice);

		// Printer toner
		System.out.println("Select printer toner capabilities");
		maxChoice = menuHandler.printerTonerMenu();
		int tonerChoice = menuHandler.intRangeInput("Please input a number in range [1, " + maxChoice + "]", 1, maxChoice);

		// Parse user choices and create printer capabilities object
		Boolean[] bools = parsePrinterCapabilities(formatChoice, tonerChoice);
		PrinterCapability printerCapability = objectHandler.createPrinterCapability(bools[canPrintA4], bools[canPrintA3], bools[canPrintBlack], bools[canPrintColor]);

		// Ask user about pricing and create printer pricing object
		PrinterPricing printerPricing = createPrinterPricing(bools);

		// Ask user about capacity and create printer capacity object
		PrinterCapacity printerCapacity = createPrinterCapacity(bools);

		printers.put("Printer" + printers.size(), objectHandler.createPrinter(printerName, printerCapability, printerPricing, printerCapacity, objectHandler.createPrinterStatus()));
	}
	
	// TODO doc
	private static Boolean[] parsePrinterCapabilities(int formatChoice, int tonerChoice) {

		boolean printA4, printA3, printBlack, printColor;
		
		// A4 only
		if(formatChoice == 1) {
			printA4 = true;
			printA3 = false;
		// A3 only
		} else if(formatChoice == 2) {
			printA4 = false;
			printA3 = true;
		// A4 and A3
		} else {
			printA4 = true;
			printA3 = true;
		}
		
		// Black only
		if(tonerChoice == 1) {
			printBlack = true;
			printColor = false;
		// Color only
		} else if(tonerChoice == 2) {
			printBlack = false;
			printColor = true;
		// Black and Color
		} else {
			printBlack = true;
			printColor = true;
		}
		
		Boolean[] bools = {printA4, printA3, printBlack, printColor};
		return bools;
	}
	
	// TODO doc
	private static PrinterCapacity createPrinterCapacity(Boolean[] bools) {
		
		// Init all pricing to 0
		int numA4, numA3, maxBlack, maxColor;
		numA4 = 0;
		numA3 = 0;
		maxBlack = 0;
		maxColor = 0;
		
		// Can print A4
		if(bools[canPrintA4]) {
			numA4 = menuHandler.intGTEInput("Please input A4 paper capacity (must be > 0)", 1);
		}
		
		// Can print A3
		if(bools[canPrintA4] && bools[canPrintColor]) {
			numA3 = menuHandler.intGTEInput("Please input A3 paper capacity (must be > 0)", 1);
		}
		
		// Can print Black
		if(bools[canPrintA3] && bools[canPrintBlack]) {
			maxBlack = menuHandler.intGTEInput("Please input black toner capacity (must be > 0)", 1);
		}
		
		// Can print Color
		if(bools[canPrintA3] && bools[canPrintColor]) {
			maxColor = menuHandler.intGTEInput("Please input color toner capacity (must be > 0)", 1);
		}
		
		return objectHandler.createPrinterCapacity(numA4, numA3, maxBlack, maxColor);
	}
	
	// TODO doc
	private static PrinterPricing createPrinterPricing(Boolean[] bools) {
		
		// Init all pricing to 0
		double priceA4B, priceA4C, priceA3B, priceA3C;
		priceA4B = 0.0;
		priceA4C = 0.0;
		priceA3B = 0.0;
		priceA3C = 0.0;
		
		// Can print A4 Black
		if(bools[canPrintA4] && bools[canPrintBlack]) {
			priceA4B = menuHandler.doubleGTInput("Please input A4 black per page price (must be > 0.0)", 0);
		}
		
		// Can print A4 Color
		if(bools[canPrintA4] && bools[canPrintColor]) {
			priceA4C = menuHandler.doubleGTInput("Please input A4 color per page price (must be > 0.0)", 0);
		}
		
		// Can print A3 Black
		if(bools[canPrintA3] && bools[canPrintBlack]) {
			priceA3B = menuHandler.doubleGTInput("Please input A3 black per page price (must be > 0.0)", 0);
		}
		
		// Can print A3 Color
		if(bools[canPrintA3] && bools[canPrintColor]) {
			priceA3C = menuHandler.doubleGTInput("Please input A3 color per page price (must be > 0.0)", 0);
		}
		
		return objectHandler.createPrinterPricing(priceA4B, priceA4C, priceA3B, priceA3C);
	}
	
	/**
	 * Handles user management menu options. 
	 */
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
	
	// TODO doc
	private static void createTestData() {
		
		users.put("User0", objectHandler.createUser(25.0));
		users.put("User1", objectHandler.createUser(1.0));
		
		documents.put("Doc0", objectHandler.createDocument("mydoc", 25, '4', 'C'));
		documents.put("Doc1", objectHandler.createDocument("another", 15, '3', 'B'));
		
		users.get("User0").addDocument(documents.get("Doc0"));
		users.get("User0").addDocument(documents.get("Doc1"));
		users.get("User1").addDocument(documents.get("Doc0"));
		
		PrinterCapability capability1 = objectHandler.createPrinterCapability(true, false, true, true);
		PrinterCapability capability2 = objectHandler.createPrinterCapability(false, true, true, false);
		
		PrinterPricing pricing = objectHandler.createPrinterPricing(0.03, 0.14, 0.06, 0.24);
		
		PrinterCapacity capacity1 = objectHandler.createPrinterCapacity(25, 0, 10, 30);
		PrinterCapacity capacity2 = objectHandler.createPrinterCapacity(0, 10, 15, 0);
		
		printers.put("Printer0", objectHandler.createPrinter("A4All", capability1, pricing, capacity1, objectHandler.createPrinterStatus()));
		printers.put("Printer1", objectHandler.createPrinter("A3B", capability2, pricing, capacity2, objectHandler.createPrinterStatus()));
		printers.put("Printer2", objectHandler.createPrinter("A3B-rip", capability2, pricing, capacity2, objectHandler.createPrinterStatus()));
		
		VDMSet status = new VDMSet();
		status.add("needFixing");
		printers.get("Printer2").break_(status);
	}
}
