package com.revature.ui;

import org.apache.log4j.Logger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.dao.usersDAOImp;
import com.revature.exceptions.UserNotFoundException;
import com.revature.model.Account;
import com.revature.model.Transaction;
import com.revature.model.User;
import com.revature.services.UserService;

public class EmployeeMenu implements Menu {

	private UserService userService;
	private static Logger log = Logger.getLogger(EmployeeMenu.class);
	public usersDAOImp udao = new usersDAOImp();
	List<Transaction>trans = new ArrayList<Transaction>();
	int z;
	int i;
	public EmployeeMenu() {
		this.userService = new UserService();
	}
	@Override
	public void display() throws SQLException {
		int choice = 0;
		do {
			System.out.println("=== Employee MENU ===");
			System.out.println("Please select an option below: ");
			System.out.println("1.) Back");
			System.out.println("2.) Approval menus");
			System.out.println("3.) See Transactions");
			//System.out.println("4.) Check Accounts");
			
			try {
				choice = Integer.parseInt(Menu.input.nextLine());
			} catch (NumberFormatException e) {
			}
			
			switch (choice) {
				case 1:
					break;
				case 2:
				//go to approval accounts
					Menu acctApprovalMenu = new AccountApproval();
					acctApprovalMenu.display();
					log.info("Employee made the choice of " + choice + "and went to  the accounts needing approval");
					break;
				case 3:
					getAccounts();
					break;
					//work it out here
				case 4:
					System.out.println("Accounts will display balances here");
					break;
					//logic here to pull up accounts.
				default:
					System.out.println("No valid choice entered, please try again");
			}
			
		} while(choice != 1);
	}

	public void getAccounts() throws SQLException {
		trans = udao.gettransactions();
		System.out.println(trans.toString());
			
		}
	}



