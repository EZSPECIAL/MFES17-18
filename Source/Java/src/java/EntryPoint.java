package MFESTA.java;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.overture.codegen.runtime.VDMSet;

import MFESTA.Document;
import MFESTA.Network;
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
	private static Network network = new Network();

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
				// Printer menu
				printerMenuLogic();
				break;
			case 3:
				// Network menu
				networkMenuLogic();
				break;
			case 4:
				System.exit(0);
				break;
			}
		}
	}
	
	/**
	 * Handles network management menu options. 
	 */
	private static void networkMenuLogic() {

		boolean onMenu = true;

		while(onMenu) {

			menuHandler.clrscr();
			int maxChoice = menuHandler.networkMenu();

			int intChoice = menuHandler.intRangeInput("Please input a number in range [1, " + maxChoice + "]", 1, maxChoice);

			switch(intChoice) {
			case 1:
				// List printers in network
				
				// Check network has printers
				VDMSet printerSet = network.getPrinterList();
				if(printerSet.size() == 0) {
					System.out.println("Network has no printers! Please add printers before using this menu.");
				} else {
					menuHandler.prettyPrintPrintersSet(printers, printerSet, 1);
				}
				menuHandler.waitOnKey(keyMsg);
				break;
			case 2:
				// Add printer
				
				// Check printers exist
				if(printers.size() == 0) {
					System.out.println("No printers created! Please create one before using this menu.");
					menuHandler.waitOnKey(keyMsg);
				} else {
					
					TreeMap<String, Printer> availablePrinters = new TreeMap<String, Printer>();
					
					// Check which printers aren't already in network
					for(Map.Entry<String, Printer> entry : printers.entrySet()) {
						if(!network.getPrinterList().contains(entry.getValue())) availablePrinters.put(entry.getKey(), entry.getValue());
					}
					
					// All printers already in network
					if(availablePrinters.size() == 0) {
						System.out.println("All existing printers belong to the network already!");
						menuHandler.waitOnKey(keyMsg);
						break;
					}
					
					menuHandler.prettyPrintPrinters(availablePrinters, 1);
					
					// Await valid printer input
					int printerChoice;
					do {
						printerChoice = menuHandler.intInput("Please input a number seen above.");
					} while(!availablePrinters.containsKey("Printer" + printerChoice));
					
					// Check chosen printer's name doesn't conflict with another network printer
					boolean isInvalid = false;

					String selectedPrinterName = printers.get("Printer" + printerChoice).getPrinterName();
					
					for(@SuppressWarnings("unchecked")
					Iterator<Printer> iter = network.getPrinterList().iterator(); iter.hasNext(); ) {
						Printer printer = iter.next();
						if(printer.getPrinterName().equals(selectedPrinterName)) {
							isInvalid = true;
							break;
						}
					}
					
					// Duplicate printer name
					if(isInvalid) {
						System.out.println("Network already has a printer with that name, cannot add this printer.");
						menuHandler.waitOnKey(keyMsg);
						break;
					}
					
					network.addPrinter(printers.get("Printer" + printerChoice));
				}
				break;
			case 3:
				// Remove printer
				
				// Check network has printers
				printerSet = network.getPrinterList();
				if(printerSet.size() == 0) {
					System.out.println("Network has no printers! Please add printers before using this menu.");
					menuHandler.waitOnKey(keyMsg);
					break;
				}			
				
				TreeMap<String, Printer> availablePrinters = new TreeMap<String, Printer>();
				
				// Check which printers are in the network
				for(Map.Entry<String, Printer> entry : printers.entrySet()) {
					if(network.getPrinterList().contains(entry.getValue())) availablePrinters.put(entry.getKey(), entry.getValue());
				}
				
				menuHandler.prettyPrintPrinters(availablePrinters, 1);
				
				// Await valid printer input
				int printerChoice;
				do {
					printerChoice = menuHandler.intInput("Please input a number seen above.");
				} while(!availablePrinters.containsKey("Printer" + printerChoice));
				
				// Remove printer from network
				network.removePrinter(printers.get("Printer" + printerChoice));
				
				break;
			case 4:
				// Print individual reports
				
				// Check network has printers
				printerSet = network.getPrinterList();
				if(printerSet.size() == 0) {
					System.out.println("Network has no printers! Please add printers before using this menu.");
				} else {
					network.printIndividualReport();
				}

				menuHandler.waitOnKey(keyMsg);
				break;
			case 5:
				// Print global report				
				network.printGlobalReport();
				menuHandler.waitOnKey(keyMsg);
				break;
			case 6:
				onMenu = false;
				break;
			}
		}
	}

	/**
	 * Handles printer management menu options. 
	 */
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
				
				// Check printers exist
				if(printers.size() == 0) {
					System.out.println("No printers created! Please create one before using this menu.");
					menuHandler.waitOnKey(keyMsg);
				} else {
					printDocumentMenuLogic();
				}
				break;
			case 4:
				// Break/empty printer
				
				// Check printers exist
				if(printers.size() == 0) {
					System.out.println("No printers created! Please create one before using this menu.");
					menuHandler.waitOnKey(keyMsg);
				} else {
					breakPrinterMenuLogic();
				}
				break;
			case 5:
				// Break all
				
				// Check printers exist
				if(printers.size() == 0) {
					System.out.println("No printers created! Please create one before using this menu.");
					menuHandler.waitOnKey(keyMsg);
				} else {
					
					menuHandler.prettyPrintPrinters(printers, 1);
					int printerChoice = menuHandler.intRangeInput("Please input a number in range [0, " + (printers.size() - 1) + "]", 0, printers.size() - 1);

					printers.get("Printer" + printerChoice).breakAll();
				}
				break;
			case 6:
				// Fix/refill printer
				
				// Check printers exist
				if(printers.size() == 0) {
					System.out.println("No printers created! Please create one before using this menu.");
					menuHandler.waitOnKey(keyMsg);
				} else {
					fixPrinterMenuLogic();
				}
				break;
			case 7:
				// Fix all
				
				// Check printers exist
				if(printers.size() == 0) {
					System.out.println("No printers created! Please create one before using this menu.");
					menuHandler.waitOnKey(keyMsg);
				} else {
					
					menuHandler.prettyPrintPrinters(printers, 1);
					int printerChoice = menuHandler.intRangeInput("Please input a number in range [0, " + (printers.size() - 1) + "]", 0, printers.size() - 1);

					printers.get("Printer" + printerChoice).fixAll();
				}
				break;
			case 8:
				// Print report
				
				// Check printers exist
				if(printers.size() == 0) {
					System.out.println("No printers created! Please create one before using this menu.");
				} else {
					
					menuHandler.prettyPrintPrinters(printers, 1);
					int printerChoice = menuHandler.intRangeInput("Please input a number in range [0, " + (printers.size() - 1) + "]", 0, printers.size() - 1);

					printers.get("Printer" + printerChoice).printIndividualReport();
				}
				menuHandler.waitOnKey(keyMsg);
				break;
			case 9:
				onMenu = false;
				break;
			}
		}
	}
	
	/**
	 * Handles fix printer menu options. 
	 */
	@SuppressWarnings("unchecked")
	private static void fixPrinterMenuLogic() {
	
		menuHandler.prettyPrintPrinters(printers, 1);
		int printerChoice = menuHandler.intRangeInput("Please input a number in range [0, " + (printers.size() - 1) + "]", 0, printers.size() - 1);

		Printer printer = printers.get("Printer" + printerChoice);
		VDMSet fixSet = new VDMSet();
		VDMSet printerStatus = printer.getPrinterStatus().getStatus();
		
		// Check printer has problems
		if(printerStatus.contains("operational") && printerStatus.size() == 1) {
			System.out.println("Printer has no problems, please select another.");
			menuHandler.waitOnKey(keyMsg);
			return;
		}
		
		for(Iterator<String> iter = printerStatus.iterator(); iter.hasNext(); ) {
			String status = iter.next();
			
			// Ask what to fix for each of the printer's current negative status
			if(status.equals("needA4")) {
				if(menuHandler.inputYesNo("Refill A4 paper stock? [y/n]")) fixSet.add("needA4");
			} else if(status.equals("needA3")) {
				if(menuHandler.inputYesNo("Refill A3 paper stock? [y/n]")) fixSet.add("needA3");
			} else if(status.equals("needBlackToner")) {
				if(menuHandler.inputYesNo("Refill black toner stock? [y/n]")) fixSet.add("needBlackToner");
			} else if(status.equals("needColorToner")) {
				if(menuHandler.inputYesNo("Refill color toner stock? [y/n]")) fixSet.add("needColorToner");
			} else if(status.equals("needFixing")) {
				if(menuHandler.inputYesNo("Fix malfunction in printer? [y/n]")) fixSet.add("needFixing");
			}
		}

		printer.fix(fixSet);
	}

	/**
	 * Handles break printer menu options. 
	 */
	@SuppressWarnings("unchecked")
	private static void breakPrinterMenuLogic() {
		
		menuHandler.prettyPrintPrinters(printers, 1);
		int printerChoice = menuHandler.intRangeInput("Please input a number in range [0, " + (printers.size() - 1) + "]", 0, printers.size() - 1);

		Printer printer = printers.get("Printer" + printerChoice);
		VDMSet breakSet = new VDMSet();
		
		// Ask what to break for each of the printer's capabilities
		if(printer.getPrinterCapabilities().getCanPrintA4()) {
			if(menuHandler.inputYesNo("Empty A4 paper stock? [y/n]")) breakSet.add("needA4");
		}
		
		if(printer.getPrinterCapabilities().getCanPrintA3()) {
			if(menuHandler.inputYesNo("Empty A3 paper stock? [y/n]")) breakSet.add("needA3");
		}
		
		if(printer.getPrinterCapabilities().getCanPrintBlack()) {
			if(menuHandler.inputYesNo("Empty black toner stock? [y/n]")) breakSet.add("needBlackToner");
		}
		
		if(printer.getPrinterCapabilities().getCanPrintColor()) {
			if(menuHandler.inputYesNo("Empty color toner stock? [y/n]")) breakSet.add("needColorToner");
		}
		
		if(printer.getPrinterStatus().getStatus().contains("operational")) {
			if(menuHandler.inputYesNo("Create malfunction in printer? [y/n]")) breakSet.add("needFixing");
		}
		
		printer.break_(breakSet);
	}

	/**
	 * Handles print document menu options. 
	 */
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

		// Check printers available for the chosen document
		TreeMap<String, Printer> availablePrinters = checkPrinterAvailability(toPrint);
		
		// No printer can print the chosen document
		if(availablePrinters.size() == 0) {
			menuHandler.waitOnKey(keyMsg);
			return;
		}
		
		// List available printers and await user input
		while(true) {
			menuHandler.prettyPrintPrinters(availablePrinters, 1);

			// Await valid printer input
			int printerChoice;
			do {
				printerChoice = menuHandler.intInput("Please input a number seen above.");
			} while(!availablePrinters.containsKey("Printer" + printerChoice));

			// Check user balance
			double totalCost = checkUserBalance(users.get("User" + userChoice), toPrint, printers.get("Printer" + printerChoice));
			if(totalCost != 0) {
				System.out.println("Not enough balance to print, needed: " + totalCost);
				menuHandler.waitOnKey(keyMsg);
				return;
			}

			// Confirm pages to print to user
			int numPages = printers.get("Printer" + printerChoice).numPagesToPrint(toPrint).intValue();
			boolean userConfirmation = menuHandler.inputYesNo("Can print " + numPages + "/" + toPrint.getPagesLeft() + ". Confirm?");

			if(userConfirmation) {
				printers.get("Printer" + printerChoice).print(toPrint, users.get("User" + userChoice));
				break;
			}
		}
	}

	/**
	 * Checks if the user has enough money to print the specified document at the specified
	 * printer. Returns 0 if successful, totalCost otherwise.
	 * 
	 * @param user the user to check
	 * @param doc the document to print
	 * @param printer the printer being used to print
	 * @return 0 if user can print, totalCost if not enough balance
	 */
	private static double checkUserBalance(User user, Document doc, Printer printer) {
		
		double balance = user.getBalance().doubleValue();
		int pagesToPrint = printer.numPagesToPrint(doc).intValue();
		
		char format = doc.getPageFormat();
		char toner = doc.getPageToner();

		double pricing = 0.0;

		// Get printer pricing
		if(format == '4' && toner == 'B') {
			pricing = printer.getPrinterPricing().getPriceA4Black().doubleValue();
		} else if(format == '4' && toner == 'C') {
			pricing = printer.getPrinterPricing().getPriceA4Color().doubleValue();
		} else if(format == '3' && toner == 'B') {
			pricing = printer.getPrinterPricing().getPriceA3Black().doubleValue();
		} else if(format == '3' && toner == 'C') {
			pricing = printer.getPrinterPricing().getPriceA3Color().doubleValue();
		}
		
		// Calc total cost
		double totalCost = pricing * pagesToPrint;
		
		if(balance >= totalCost) return 0;
		else return totalCost;
	}

	/**
	 * Checks all printers to see if they can print the specified document. Checks format/toner
	 * requirements, paper/toner levels and operational status.
	 * 
	 * @param doc the document to print
	 * @return the map of printers that can print the document
	 */
	private static TreeMap<String, Printer> checkPrinterAvailability(Document doc) {
		
		char format = doc.getPageFormat();
		char toner = doc.getPageToner();
			
		TreeMap<String, Printer> capablePrinters = new TreeMap<String, Printer>();
		
		// Check printers capable of printing specified document
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
			
			// Add printers capable of printing the specified document
			if(neededCapabilityExists) capablePrinters.put(entry.getKey(), printer);
		}
		
		// No capable printers found
		if(capablePrinters.size() == 0) {
			System.out.println("No printer can print the document format/toner combination! Please add a printer with the required capabilities.");
			return new TreeMap<String, Printer>();
		}
		
		boolean atLeastOnePaperTonerAvail = false;
		boolean atLeastOneOperational = false;
		
		TreeMap<String, Printer> availablePrinters = new TreeMap<String, Printer>();
		
		// Check previous printers toner and paper levels as well as operational status
		for(Map.Entry<String, Printer> entry : capablePrinters.entrySet()) {
			
			Printer printer = entry.getValue();
			
			boolean printerHasPaperToner = false;
			
			// Check printer for needed capabilities (paper/toner)
			if(format == '4' && toner == 'B') {
				printerHasPaperToner = printer.getPrinterCapacities().getNumOfSheetsA4().intValue() > 0 && printer.getPrinterCapacities().getBlackPrintsLeft().intValue() > 0;
			} else if(format == '4' && toner == 'C') {
				printerHasPaperToner = printer.getPrinterCapacities().getNumOfSheetsA4().intValue() > 0 && printer.getPrinterCapacities().getColorPrintsLeft().intValue() > 0;
			} else if(format == '3' && toner == 'B') {
				printerHasPaperToner = printer.getPrinterCapacities().getNumOfSheetsA3().intValue() > 0 && printer.getPrinterCapacities().getBlackPrintsLeft().intValue() > 0;
			} else if(format == '3' && toner == 'C') {
				printerHasPaperToner = printer.getPrinterCapacities().getNumOfSheetsA3().intValue() > 0 && printer.getPrinterCapacities().getColorPrintsLeft().intValue() > 0;
			}
			
			// Does printer have enough paper/toner for at least 1 page of the document?
			if(printerHasPaperToner) {
				atLeastOnePaperTonerAvail = true;
				
				// Is the printer operational?
				if(printer.getPrinterStatus().getStatus().contains("operational")) {
					atLeastOneOperational = true;
					availablePrinters.put(entry.getKey(), printer);
				}
			}
		}
		
		// No capable printer has paper/toner left
		if(!atLeastOnePaperTonerAvail) {
			System.out.println("Printers that can print the document found but they're all out of paper/toner. Please use the refill menu.");
			return new TreeMap<String, Printer>();
		}
		
		// Printers found are all non operational
		if(!atLeastOneOperational) {
			System.out.println("Printers that can print the document found but they're all in need of fixing. Please use the fix menu.");
			return new TreeMap<String, Printer>();
		}
		
		return availablePrinters;
	}
	
	/**
	 * Handles create printer menu options.
	 */
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
	
	/**
	 * Parses user choice as an integer and returns it as an array of printer capabilities.
	 * 
	 * @param formatChoice the chosen format
	 * @param tonerChoice the chosen toner
	 * @return the array of booleans representing the printer's capabilities (canPrintA4, canPrintA3, canPrintBlack, canPrintColor)
	 */
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
	
	/**
	 * Prompts the user for a printer capacity object using the specified
	 * capabilities.
	 * 
	 * @param bools the printer capabilities
	 * @return the printer capacity created by the user
	 */
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
		if(bools[canPrintA3]) {
			numA3 = menuHandler.intGTEInput("Please input A3 paper capacity (must be > 0)", 1);
		}
		
		// Can print Black
		if(bools[canPrintBlack]) {
			maxBlack = menuHandler.intGTEInput("Please input black toner capacity (must be > 0)", 1);
		}
		
		// Can print Color
		if(bools[canPrintColor]) {
			maxColor = menuHandler.intGTEInput("Please input color toner capacity (must be > 0)", 1);
		}
		
		return objectHandler.createPrinterCapacity(numA4, numA3, maxBlack, maxColor);
	}
	
	/**
	 * Prompts the user for a printer pricing object using the specified
	 * capabilities.
	 * 
	 * @param bools the printer capabilities
	 * @return the printer pricing created by the user
	 */
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
				// Add balance to user
				
				// Check users exist
				if(users.size() == 0) {
					System.out.println("No users created! Please create one before using this menu.");
					menuHandler.waitOnKey(keyMsg);
				} else {
					
					menuHandler.prettyPrintUsers(users, numColumns);
					int userChoice = menuHandler.intRangeInput("Please input a number in range [0, " + (users.size() - 1) + "]", 0, users.size() - 1);
					
					double addBal = menuHandler.doubleGTInput("Please input balance to add (must be > 0.0)", 0);
					
					// Add balance to chosen user
					users.get("User" + userChoice).deposit(addBal);
				}

				break;
			case 4:
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
			case 5:
				// Add document to user

				// Check users exist
				if(users.size() == 0) {
					System.out.println("No users created! Please create one before using this menu.");
					menuHandler.waitOnKey(keyMsg);
					break;
				}
				
				menuHandler.prettyPrintUsers(users, numColumns);
				userChoice = menuHandler.intRangeInput("Please input a number in range [0, " + (users.size() - 1) + "]", 0, users.size() - 1);
				
				// Create document to add
				Document toAdd = createDocumentMenuLogic(users.get("User" + userChoice));
				
				// User backed out of menu
				if(toAdd == null) {
					break;
				}
				
				users.get("User" + userChoice).addDocument(toAdd);
				
				break;
			case 6:
				onMenu = false;
				break;
			}
		}
	}

	/**
	 * Checks whether the user is adding a document with a duplicate name.
	 * 
	 * @param user the user adding a document
	 * @param name the name of the new document
	 * @return whether the new document conflicts with an existing user document
	 */
	private static boolean checkDuplicateDocName(User user, String name) {

		// Check if document name is already in user's documents
		boolean isInvalid = false;

		for(@SuppressWarnings("unchecked")
		Iterator<Document> iter = user.getDocumentList().iterator(); iter.hasNext(); ) {
			Document doc = iter.next();
			if(doc.getDocName().equals(name)) {
				isInvalid = true;
				break;
			}
		}
		
		return isInvalid;
	}
	
	/**
	 * Handles document creation menu options. 
	 */
	private static Document createDocumentMenuLogic(User user) {

		boolean onMenu = true;

		while(onMenu) {

			menuHandler.clrscr();
			int maxChoice = menuHandler.createDocumentMenu();

			int intChoice = menuHandler.intRangeInput("Please input a number in range [1, " + maxChoice + "]", 1, maxChoice);

			// Check exit option
			if(intChoice == maxChoice) {
				onMenu = false;
				return null;
			} else {

				int numPages = menuHandler.intGTEInput("Please input the number of pages the document has (must be > 0)", 1);
				
				String docName;
				do {
					docName = menuHandler.inputString("Please input the document name (cannot be empty)");
					
					if(checkDuplicateDocName(user, docName)) {
						System.out.println("Duplicate doc name, please pick another");
					} else break;
				} while(true);
				
				
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
		
		// Return latest document
		return documents.get("Doc" + (documents.size() - 1));
	}
	
	/**
	 * Creates test data to be used for debugging the Java interface. Used by supplying any amount of command line arguments (!= 0).
	 */
	@SuppressWarnings("unchecked")
	private static void createTestData() {
		
		users.put("User0", objectHandler.createUser(25.0));
		users.put("User1", objectHandler.createUser(1.0));
		
		documents.put("Doc0", objectHandler.createDocument("mydoc", 25, '4', 'C'));
		documents.put("Doc1", objectHandler.createDocument("another", 15, '3', 'B'));
		
		users.get("User0").addDocument(documents.get("Doc0"));
		users.get("User1").addDocument(documents.get("Doc1"));
		
		PrinterCapability capability1 = objectHandler.createPrinterCapability(true, false, true, true);
		PrinterCapability capability2 = objectHandler.createPrinterCapability(false, true, true, false);
		PrinterCapability capability3 = objectHandler.createPrinterCapability(false, true, true, false);
		
		PrinterPricing pricing = objectHandler.createPrinterPricing(0.03, 0.14, 0.06, 0.24);
		
		PrinterCapacity capacity1 = objectHandler.createPrinterCapacity(25, 0, 10, 30);
		PrinterCapacity capacity2 = objectHandler.createPrinterCapacity(0, 10, 15, 0);
		PrinterCapacity capacity3 = objectHandler.createPrinterCapacity(0, 10, 15, 0);
		
		printers.put("Printer0", objectHandler.createPrinter("A4All", capability1, pricing, capacity1, objectHandler.createPrinterStatus()));
		printers.put("Printer1", objectHandler.createPrinter("A3B", capability2, pricing, capacity2, objectHandler.createPrinterStatus()));
		printers.put("Printer2", objectHandler.createPrinter("A3B-rip", capability3, pricing, capacity3, objectHandler.createPrinterStatus()));
		
		VDMSet status = new VDMSet();
		status.add("needFixing");
		printers.get("Printer2").break_(status);
	}
}
