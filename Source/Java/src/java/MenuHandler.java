package MFESTA.java;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import org.overture.codegen.runtime.VDMSet;

import MFESTA.Document;
import MFESTA.User;

public class MenuHandler {

	/**
	 * Prints the main menu.
	 * 
	 * @return the number of choices available
	 */
	public int mainMenu() {

		System.out.println("1 - User menu");
		System.out.println("2 - Document menu");
		System.out.println("3 - Printer menu");

		return 3;
	}

	/**
	 * Prints the user menu.
	 * 
	 * @return the number of choices available
	 */
	public int userMenu() {

		System.out.println("1 - Create user");
		System.out.println("2 - List users (summary)");
		System.out.println("3 - List user documents");
		System.out.println("4 - Add a document to a user");
		System.out.println("5 - Remove a document from a user");
		System.out.println("6 - Back to main menu");

		return 6;
	}

	/**
	 * Prints the document menu.
	 * 
	 * @return the number of choices available
	 */
	public int documentMenu() {

		System.out.println("1 - Create document");
		System.out.println("2 - List documents");
		System.out.println("3 - Back to main menu");

		return 3;
	}

	/**
	 * Prints the create document menu.
	 * 
	 * @return the number of choices available
	 */
	public int createDocumentMenu() {

		System.out.println("1 - A4 Black");
		System.out.println("2 - A4 Color");
		System.out.println("3 - A3 Black");
		System.out.println("4 - A3 Color");
		System.out.println("5 - Back to document menu");

		return 5;
	}

	// TODO doc
	public int printerMenu() {
		
		System.out.println("1 - Create printer");
		System.out.println("2 - List printers");
		System.out.println("3 - Print document");
		System.out.println("4 - Break printer");
		System.out.println("5 - Fix printer");
		System.out.println("6 - Print report");
		System.out.println("7 - Back to main menu");
		
		return 7;
	}
	
	// TODO doc
	public int printerFormatMenu() {
		
		System.out.println("1 - A4");
		System.out.println("2 - A3");
		System.out.println("3 - A4/A3");
		
		return 3;
	}
	
	// TODO doc
	public int printerTonerMenu() {
		
		System.out.println("1 - Black");
		System.out.println("2 - Color");
		System.out.println("3 - Black/Color");
		
		return 3;
	}
	
	/**
	 * Shows the specified message and waits on ENTER key input.
	 * 
	 * @param msg the message to print
	 */
	public void waitOnKey(String msg) {

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println(msg);
		sc.nextLine();
	}

	/**
	 * Prints a message and waits on the user to input a string.
	 * 
	 * @param msg the message to print
	 * @return the user selected string
	 */
	public String inputString(String msg) {

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		
		String input;
		do {
			System.out.println(msg);
			input = sc.nextLine();
		} while(input.length() == 0);

		return input;
	}

	/**
	 * Prints a message and waits on user to input an integer.
	 * 
	 * @param msg the message to print
	 * @return the user selected integer
	 */
	public int intInput(String msg) {

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		int number = -1;
		do {
			System.out.println(msg);
			while (!sc.hasNextInt()) {
				System.out.println(msg);
				sc.next();
			}
			number = sc.nextInt();
		} while (number == -1);

		return number;
	}
	
	/**
	 * Prints a message and waits on user to input an integer
	 * between the two bounds.
	 * 
	 * @param msg the message to print
	 * @param low the lower bound
	 * @param high the upper bound
	 * @return the user selected integer
	 */
	public int intRangeInput(String msg, int low, int high) {

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		int number;
		do {
			System.out.println(msg);
			while (!sc.hasNextInt()) {
				System.out.println(msg);
				sc.next();
			}
			number = sc.nextInt();
		} while (!(number >= low && number <= high));

		return number;
	}

	/**
	 * Prints a message and waits on user to input an integer
	 * greater or equal to the bound.
	 * 
	 * @param msg the message to print
	 * @param low the lower bound
	 * @return the user selected integer
	 */
	public int intGTEInput(String msg, int low) {

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		int number;
		do {
			System.out.println(msg);
			while (!sc.hasNextInt()) {
				System.out.println(msg);
				sc.next();
			}
			number = sc.nextInt();
		} while (number < low);

		return number;
	}

	/**
	 * Prints a message and waits on user to input a double
	 * greater than the bound.
	 * 
	 * @param msg the message to print
	 * @param low the lower bound
	 * @return the user selected double
	 */
	public double doubleGTInput(String msg, double low) {

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		double number;
		do {
			System.out.println(msg);
			while (!sc.hasNextDouble()) {
				System.out.println(msg);
				sc.next();
			}
			number = sc.nextDouble();
		} while (number <= low);

		return number;
	}
	
	/**
	 * Prints a message and waits on user to input a double
	 * greater or equal to the bound.
	 * 
	 * @param msg the message to print
	 * @param low the lower bound
	 * @return the user selected double
	 */
	public double doubleGTEInput(String msg, double low) {

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		double number;
		do {
			System.out.println(msg);
			while (!sc.hasNextDouble()) {
				System.out.println(msg);
				sc.next();
			}
			number = sc.nextDouble();
		} while (number < low);

		return number;
	}

	/**
	 * Prints all documents contained in the VDM set provided along with their name,
	 * pages left to print / total pages, page format and toner requirement.
	 * 
	 * @param documentMap the map to print
	 * @param documentSet the VDM set to print
	 * @param numColumns the number of columns to use
	 */
	public void prettyPrintDocumentsSet(TreeMap<String, Document> documentMap, VDMSet documentSet, int numColumns) {
		
		int remaining = documentSet.size();
		int columnCount = 1;

		for(Map.Entry<String, Document> entry : documentMap.entrySet()) {
			
			if(!documentSet.contains(entry.getValue())) continue;
			
			if(remaining > 1 && columnCount != numColumns) {
				System.out.print(entry.getKey() + " - name: " + entry.getValue().getDocName() +
						" - pages: " + entry.getValue().getPagesLeft() + "/" + entry.getValue().getNumPages() +
						" - format: " + entry.getValue().getPageFormat() + entry.getValue().getPageToner() + " | ");
			} else {
				System.out.print(entry.getKey() + " - name: " + entry.getValue().getDocName() +
						" - pages: " + entry.getValue().getPagesLeft() + "/" + entry.getValue().getNumPages() +
						" - format: " + entry.getValue().getPageFormat() + entry.getValue().getPageToner());
			}

			if(columnCount == numColumns && remaining > 0) {
				System.out.println();
				columnCount = 0;
			}
			columnCount++;
			remaining--;
		}

		System.out.println();
	}
	
	/**
	 * Prints all users along with their balance and number of documents.
	 * 
	 * @param map the map to print
	 * @param numColumns the number of columns to use
	 */
	public void prettyPrintUsers(TreeMap<String, User> map, int numColumns) {

		int remaining = map.size();
		int columnCount = 1;

		for(Map.Entry<String, User> entry : map.entrySet()) {
			if(remaining > 1 && columnCount != numColumns) {
				System.out.print(entry.getKey() + " - bal: " + entry.getValue().getBalance() +
						" - #docs: " + entry.getValue().getDocumentList().size() + " | ");
			} else {
				System.out.print(entry.getKey() + " - bal: " + entry.getValue().getBalance() +
						" - #docs: " + entry.getValue().getDocumentList().size());
			}

			if(columnCount == numColumns && remaining > 0) {
				System.out.println();
				columnCount = 0;
			}
			columnCount++;
			remaining--;
		}

		System.out.println();
	}

	/**
	 * Prints all documents along with their name, pages left to print / total pages,
	 * page format and toner requirement.
	 * 
	 * @param map the map to print
	 * @param numColumns the number of columns to use
	 */
	public void prettyPrintDocuments(TreeMap<String, Document> map, int numColumns) {

		int remaining = map.size();
		int columnCount = 1;

		for(Map.Entry<String, Document> entry : map.entrySet()) {
			if(remaining > 1 && columnCount != numColumns) {
				System.out.print(entry.getKey() + " - name: " + entry.getValue().getDocName() +
						" - pages: " + entry.getValue().getPagesLeft() + "/" + entry.getValue().getNumPages() +
						" - format: " + entry.getValue().getPageFormat() + entry.getValue().getPageToner() + " | ");
			} else {
				System.out.print(entry.getKey() + " - name: " + entry.getValue().getDocName() +
						" - pages: " + entry.getValue().getPagesLeft() + "/" + entry.getValue().getNumPages() +
						" - format: " + entry.getValue().getPageFormat() + entry.getValue().getPageToner());
			}

			if(columnCount == numColumns && remaining > 0) {
				System.out.println();
				columnCount = 0;
			}
			columnCount++;
			remaining--;
		}

		System.out.println();
	}

	/**
	 * Prints all the keys of a generic map.
	 * 
	 * @param map the map to print
	 * @param numColumns the number of columns to use
	 */
	public void prettyPrintMap(TreeMap<String, ?> map, int numColumns) {
		
		int remaining = map.size();
		int columnCount = 1;

		for(Map.Entry<String, ?> entry : map.entrySet()) {
			if(remaining > 1 && columnCount != numColumns) {
				System.out.print(entry.getKey() + " | ");
			} else {
				System.out.print(entry.getKey());
			}

			if(columnCount == numColumns && remaining > 0) {
				System.out.println();
				columnCount = 0;
			}
			columnCount++;
			remaining--;
		}

		System.out.println();
	}

	/**
	 * System dependent clear screen. 
	 */
	public void clrscr() {

		try {
			if (System.getProperty("os.name").contains("Windows"))
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			else
				Runtime.getRuntime().exec("clear");
		} catch (IOException | InterruptedException ex) {
			System.out.println("Error clearing console!");
		}
	}
}
