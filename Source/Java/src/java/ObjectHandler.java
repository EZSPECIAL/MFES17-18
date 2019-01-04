package MFESTA.java;

import MFESTA.Document;
import MFESTA.Printer;
import MFESTA.PrinterCapability;
import MFESTA.PrinterCapacity;
import MFESTA.PrinterPricing;
import MFESTA.PrinterStatus;
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
	public Document createDocument(String name, int pages, char format, char toner) {
		return new Document(name, pages, format, toner);
	}
	
	/**
	 * Creates a Printer object using the specified parameters.
	 * 
	 * @param printA4 whether the printer can use A4 paper
	 * @param printA3 whether the printer can use A3 paper
	 * @param printBlack whether the printer can use black toner
	 * @param printColor whether the printer can use color toner
	 * @return the created printer
	 */
	public PrinterCapability createPrinterCapability(boolean printA4, boolean printA3, boolean printBlack, boolean printColor) {
		return new PrinterCapability(printA4, printA3, printBlack, printColor);
	}
	
	/**
	 * Creates a PrinterPricing object using the specified parameters.
	 * 
	 * @param priceA4B price of printing A4 Black pages
	 * @param priceA4C price of printing A4 Color pages
	 * @param priceA3B price of printing A3 Black pages
	 * @param priceA3C price of printing A3 Color pages
	 * @return the created printer pricing
	 */
	public PrinterPricing createPrinterPricing(double priceA4B, double priceA4C, double priceA3B, double priceA3C) {
		return new PrinterPricing(priceA4B, priceA4C, priceA3B, priceA3C);
	}

	/**
	 * Creates a PrinterCapacity object using the specified parameters.
	 * 
	 * @param numA4 the A4 paper max capacity
	 * @param numA3 the A3 paper max capacity
	 * @param maxBlack the black toner max capacity
	 * @param maxColor the color toner max capacity
	 * @return the created printer capacity
	 */
	public PrinterCapacity createPrinterCapacity(int numA4, int numA3, int maxBlack, int maxColor) {
		return new PrinterCapacity(numA4, numA3, maxBlack, maxColor);
	}
	
	/**
	 * Creates a PrinterStatus object.
	 * 
	 * @return the created printer status
	 */
	public PrinterStatus createPrinterStatus() {
		return new PrinterStatus();
	}

	/**
	 * Creates a Printer object using the specified parameters.
	 * 
	 * @param name the printer name
	 * @param capabilities the printer capabilities
	 * @param pricing the printer pricing
	 * @param capacities the printer capacities
	 * @param status the printer status
	 * @return the created printer
	 */
	public Printer createPrinter(String name, PrinterCapability capabilities, PrinterPricing pricing, PrinterCapacity capacities, PrinterStatus status) {
		return new Printer(name, capabilities, pricing, capacities, status);
	}
}