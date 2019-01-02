package MFESTA.java;

import java.util.HashMap;
import java.util.Map;

import MFESTA.Network;
import MFESTA.User;

public class EntryPoint {

	private static MenuHandler menuHandler = new MenuHandler();
	private static ObjectHandler objectHandler = new ObjectHandler();
	
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
		
		menuHandler.clrscr();
		menuHandler.userMenu();
		
		int intChoice = menuHandler.intRangeInput("Please input a number in range [1, 2]", 1, 2);
		
		switch(intChoice) {
		case 1:
			double initBalance = menuHandler.doubleGTInput("Please input an initial balance for this user (must be >= 0.0)", 0);
			users.put("User" + users.size(), objectHandler.createUser(initBalance));
			break;
		case 2:
			for(Map.Entry<String, User> entry : users.entrySet()) {
				System.out.println(entry.getKey() + " - bal: " + entry.getValue().getBalance() + " | #docs: " + entry.getValue().getDocumentList().size());
			}
			break;
		}
	}
}
