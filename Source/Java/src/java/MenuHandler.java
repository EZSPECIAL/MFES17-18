package MFESTA.java;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import MFESTA.User;

public class MenuHandler {

	/**
	 * Prints the main menu. 
	 */
	public void mainMenu() {
		
		System.out.println("1 - User menu");
	}
	
	/**
	 * Prints the user menu.
	 */
	public void userMenu() {
		
		System.out.println("1 - Create user");
		System.out.println("2 - List users (summary)");
		System.out.println("3 - List user documents");
		System.out.println("4 - Back to main menu");
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
		} while (number < low);
		
		return number;
	}

	/**
	 * Prints all users along with their balance and number of documents.
	 * 
	 * @param map the map to print
	 * @param numColumns the number of columns to use
	 */
	public void prettyPrintUsers(HashMap<String, User> map, int numColumns) {
		
		int remaining = map.size();
		int columnCount = 1;
		
		for(Map.Entry<String, User> entry : map.entrySet()) {
			if(remaining > 1 && columnCount != numColumns) {
				System.out.print(entry.getKey() + " - bal: " + entry.getValue().getBalance() + " - #docs: " + entry.getValue().getDocumentList().size() + " | ");
			} else {
				System.out.print(entry.getKey() + " - bal: " + entry.getValue().getBalance() + " - #docs: " + entry.getValue().getDocumentList().size());
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
	public void prettyPrintMap(HashMap<String, ?> map, int numColumns) {
		
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
