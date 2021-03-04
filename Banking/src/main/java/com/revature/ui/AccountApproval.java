package com.revature.ui;

import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.model.Account;


public class AccountApproval implements Menu {
	ArrayList<Account> accts = new ArrayList<Account>();
	Connection con;
	int acctNum;
	private static Logger log = Logger.getLogger(AccountApproval.class);
	@Override
	public void display() throws SQLException {
		int choice = 0;
		do {
			System.out.println("=== Approval MENU ===");
			System.out.println("Please select an option below: ");
			System.out.println("1.) Back");
			System.out.println("2.) Needing Approval List");
			System.out.println("3.) Get Account");
			System.out.println("4.) Check Accounts");
			
			try {
				choice = Integer.parseInt(Menu.input.nextLine());
			} catch (NumberFormatException e) {
			}
			
			switch (choice) {
				case 1:
					break;
				case 2:
				//get Accounts that need Approval
					Menu needApproval = new NeedingApprovalMenu();
					needApproval.display();
					break;
				case 3:
					acctNum = getAcctNum();
					
					//work it out here
				case 4:
				//	System.out.println("Accounts will display balances here");
					//logic here to pull up accounts.
				default:
					System.out.println("No valid choice entered, please try again");
			}
			
		} while(choice != 1);
	}
	public ArrayList<Account> getAllAccounts(Connection con) throws SQLException {
		ArrayList<Account> user = new ArrayList<Account>();
		String sql = "SELECT * FROM \"Banking\".accounts where approved = true ;";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			Account newUser;
			int id = rs.getInt("accountNumber");
			String username = rs.getString("username");
			double balance = rs.getDouble("balance");
			boolean status = rs.getBoolean("approved");
			newUser = new Account(id, username, balance, status);
			user.add(newUser);
		}
		return user;
	}
	
	private int getAcctNum() {
		System.out.println("Enter the Account Number: ");
		int input = Integer.parseInt(Menu.input.nextLine().trim());

		return input;
	}

	
	}
