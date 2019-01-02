package MFESTA.java;

import java.util.HashMap;
import java.util.Map;

import MFESTA.Network;
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

	public static void main(String[] args) {

		mainMenuLogic();	

		Network net = new Network();
		//net.printGlobalReport();
	}

	private static void mainMenuLogic() {

		while(true) {

			menuHandler.clrscr();
			menuHandler.mainMenu();

			int userChoice = menuHandler.intRangeInput("Please input a number in range [1, 1]", 1, 1);

			switch(userChoice) {
			case 1:
				userMenuLogic();
				break;
			}
		}
	}

	private static void userMenuLogic() {

		boolean onMenu = true;
		
		while(onMenu) {

			menuHandler.clrscr();
			menuHandler.userMenu();

			int intChoice = menuHandler.intRangeInput("Please input a number in range [1, 4]", 1, 4);

			switch(intChoice) {
			case 1:
				double initBalance = menuHandler.doubleGTInput("Please input an initial balance for the user (must be >= 0.0)", 0);
				users.put("User" + users.size(), objectHandler.createUser(initBalance));
				break;
			case 2:
				if(users.size() == 0) {
					System.out.println("No users created! Please create one before using this menu.");
				} else {
					menuHandler.prettyPrintUsers(users, numColumns);
					menuHandler.waitOnKey(keyMsg);
				}
				break;
			case 3:
				if(users.size() == 0) {
					System.out.println("No users created! Please create one before using this menu.");
				} else {
					menuHandler.prettyPrintUsers(users, numColumns);
					int userChoice = menuHandler.intRangeInput("Please input a number in range [0, " + (users.size() - 1) + "]", 0, users.size() - 1);
					menuHandler.waitOnKey(keyMsg);
				}
				break;
			case 4:
				onMenu = false;
				break;
			}
		}
	}
}
