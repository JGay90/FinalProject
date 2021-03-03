package com.revature.ui;

import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.model.Account;
import com.revature.util.ConnectionUtil;

public class NeedingApprovalMenu implements Menu {
	ArrayList<Account> accts = new ArrayList<Account>();
	Connection con;
	int acctNum;
	private static Logger log = Logger.getLogger(NeedingApprovalMenu.class);

	@Override
	public void display() throws SQLException {
		int choice = 0;
		do {
			System.out.println("=== Needing Approval MENU ===");
			System.out.println("Please select an option below: ");
			System.out.println("1.) Back");
			System.out.println("2.) Get Approval List");
			System.out.println("3.) Close Account");
			System.out.println("4.) Approve Account");

			try {
				choice = Integer.parseInt(Menu.input.nextLine());
			} catch (NumberFormatException e) {
			}

			switch (choice) {
			case 1:
				break;
			case 2:
				// get Accounts that need Approval
				accts = getAllAccounts(con = ConnectionUtil.getConnection());
				System.out.println(accts.toString());
				log.info("Employee made the choice of " + choice + " and retrieved the approval list");
				break;
			case 3:
				//Deny or Close Accts that are pending approval
				acctNum = getAcctNum();
				denyAcct(con, acctNum);
				System.out.println(accts.toString());
				log.info("Employee made the choice of " + choice + " and closed the account");
				break;
			
			case 4:
				//Approving pending Accts
				acctNum = getAcctNum();
				approveAcct(con, acctNum);
				accts = getAllAccounts(con = ConnectionUtil.getConnection());
				System.out.println(accts.toString());
				log.info("Employee made the choice of " + choice + " and approved the account");
				break;

			default:
				System.out.println("No valid choice entered, please try again");
			}

		} while (choice != 1);
	}

	public ArrayList<Account> getAllAccounts(Connection con) throws SQLException {
		ArrayList<Account> user = new ArrayList<Account>();
		String sql = "SELECT * FROM \"Banking\".accounts where approved = false ;";
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

	public void approveAcct(Connection con, int acctNum) throws SQLException {
		String sql = "UPDATE \"Banking\".accounts SET approved=true WHERE accountnumber= ?;";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, acctNum);
		pstmt.executeUpdate();

	}

	private int getAcctNum() {
		System.out.println("Enter the Account Number: ");
		int input = Integer.parseInt(Menu.input.nextLine().trim());

		return input;
	}

	public void denyAcct(Connection con, int acctNum) throws SQLException {
		String sql = "UPDATE \"Banking\".accounts SET closed=true WHERE accountnumber= ?;";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, acctNum);
		pstmt.executeUpdate();

	}

	@Override
	public void display(String un) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
