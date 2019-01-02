package MFESTA.java;

import java.io.IOException;
import java.util.Scanner;

public class MenuHandler {

	public void mainMenu() {
		
		System.out.println("1 - User menu");
	}
	
	public void userMenu() {
		
		System.out.println("1 - Create user");
		System.out.println("2 - List users");
	}
	
	public void createUserMenu() {
		
	}
	
	public int intRangeInput(String msg, int low, int high) {
		
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
	
	public double doubleGTInput(String msg, double low) {
		
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
