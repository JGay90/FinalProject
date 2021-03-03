package com.revature.ui;

import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.SQLException;

import com.revature.dao.usersDAOImp;
import com.revature.util.ConnectionUtil;

public class MainMenu implements Menu {
	usersDAOImp udao = new usersDAOImp();
	String un;
	String pw;
	Connection con;
	private static Logger log = Logger.getLogger(MainMenu.class);
	public void display() throws SQLException {
		System.out.println("Welcome to the application!");
		
		int choice = 0;
		
		do {
			System.out.println("=== MAIN MENU ===");
			System.out.println("Please select an option below: ");
			System.out.println("1.) Exit Application");
			System.out.println("2.) User Login");
			System.out.println("3.) New User Menu");
			System.out.println("4.) Employee Login");
			
			try {
				choice = Integer.parseInt(Menu.input.nextLine());
			} catch (NumberFormatException e) {
			}
			
			switch (choice) {
				case 1:
					break;
				case 2:
					un = getUsername();
					Menu userMenu = new UserMenu(un);
					pw = getPassword();
					 con = ConnectionUtil.getConnection();
					
					if(udao.logIn(un, pw, con)!=true)
					{
						System.out.println("Input not valid");
						log.warn("User made the choice of " + choice + "and failed to log in");
					}
					else {
					userMenu.display();
					log.info("User made the choice of " + choice + "and logged into the User Menu");
					}
					break;
				
				  case 3:
					  Menu newUserMenu = new NewUserMenu();
					  newUserMenu.display(); 
					  log.info("User made the choice of " + choice + "They went into the new user menu");
					  break;
				  case 4:
					  Menu employeeMenu = new EmployeeMenu();
					   un = getUsername();
					   pw = getPassword();
					   con = ConnectionUtil.getConnection();
						
						if(udao.employeelogIn(un, pw, con)!=true)
						{
							System.out.println("Input not valid");
							log.warn("User made the choice of " + choice + "and failed to log in");
						}
						else {
					  employeeMenu.display();
					  log.info("User made the choice of " + choice + "and logged into the Employee Menu");
					  
					  break;
						}
				 
				default:
					System.out.println("No valid choice entered, please try again");
			}
			
		} while(choice != 1);
		
	}
	private String getUsername() {
		System.out.println("Enter your Username: ");
		String input = Menu.input.nextLine().trim();
		
		return input;
	}
	private String getPassword() {
		System.out.println("Enter your Password: ");
		String input = Menu.input.nextLine().trim();
		
		return input;
	}
	@Override
	public void display(String un) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
}
