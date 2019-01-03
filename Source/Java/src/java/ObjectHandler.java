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
	
	// TODO doc
	public PrinterCapability createPrinterCapability(boolean printA4, boolean printA3, boolean printBlack, boolean printColor) {
		return new PrinterCapability(printA4, printA3, printBlack, printColor);
	}
	
	// TODO doc
	public PrinterPricing createPrinterPricing(double priceA4B, double priceA4C, double priceA3B, double priceA3C) {
		return new PrinterPricing(priceA4B, priceA4C, priceA3B, priceA3C);
	}
	
	// TODO doc
	public PrinterCapacity createPrinterCapacity(int numA4, int numA3, int maxBlack, int maxColor) {
		return new PrinterCapacity(numA4, numA3, maxBlack, maxColor);
	}
	
	// TODO doc
	public PrinterStatus createPrinterStatus() {
		return new PrinterStatus();
	}
	
	// TODO doc
	public Printer createPrinter(String name, PrinterCapability capabilities, PrinterPricing pricing, PrinterCapacity capacities, PrinterStatus status) {
		return new Printer(name, capabilities, pricing, capacities, status);
	}
}