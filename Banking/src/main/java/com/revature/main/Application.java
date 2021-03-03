package com.revature.main;


import java.sql.SQLException;

import com.revature.ui.MainMenu;
import com.revature.ui.Menu;

public class Application {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		Menu mainMenu = new MainMenu(); // shortcut for importing is ctrl + shift + o
		mainMenu.display();
		
		
		// This is the end of the application
		// So we can close our scanner
		Menu.input.close();
		System.out.println("Application closing!");
		

	}

}
