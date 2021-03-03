package com.revature.ui;


import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.revature.dao.usersDAOImp;
import com.revature.exceptions.UserNotFoundException;
import com.revature.model.Account;
import com.revature.model.Transaction;
import com.revature.model.User;
import com.revature.services.UserService;
import com.revature.util.ConnectionUtil;

public class NewUserMenu implements Menu {

	List<Account> newaccts = new ArrayList<>();
	UserService uServe = new UserService();
	Connection c;
	Transaction trans = new Transaction();
	String adate;
	public LocalDate date;
	public List<Transaction>transacts = new ArrayList<Transaction>();
	private static Logger log = Logger.getLogger(NewUserMenu.class);
	int choice;
	@Override
	public void display() throws SQLException {
		choice = 0;
		
		do {
			System.out.println("===NEW USER MENU ===");
			System.out.println("Please select an option below: ");
			System.out.println("1.) Back");
			System.out.println("2.) New Customer Application");
			System.out.println("3.) New Acount Application");
			System.out.println("4.) New Customer and Account application");
			
			try {
				choice = Integer.parseInt(Menu.input.nextLine());
			} catch (NumberFormatException e) {
			}
			
			switch (choice) {
				case 1:
					break;
				case 2:
				{
					selectNewUserAcct();				
					break;
					
				}	
				case 3:
				{
					selectNewBankAcct();
					break;
				}
				case 4:
				{
					selectNewBoth();
					break;
				}
				default:
					System.out.println("No valid choice entered, please try again");
			}
			
		} while(choice != 1);
	}
	public void selectNewBoth() throws SQLException
	{
		String username = getUsernameInput();
		String firstName = getUserFirstName();
		String lastName = getUserLastName();
		String pw = getUserPassword();
	   	User newUser = new User(firstName,lastName,username,pw);
		uServe.addToDB(newUser);
		double bal = Double.parseDouble(getStartingDeposit());
		Account newacct = new Account();
		newacct.setAcctBal(bal);
		newacct.setApproved(false);
		newaccts.add(newacct);
		Random rng = new Random();
		newacct.setAcctNum(rng.nextInt(999999999));
		uServe.addToDB(newacct,newUser.getUsername());
		log.info("User made the choice of " + choice + "and made a new user login and Bank account");
	}
	public void selectNewBankAcct() throws SQLException {
		String un = getAUsernameInput();
		double bal = Double.parseDouble(getStartingDeposit());
		Account newacct = new Account();
		newacct.setAcctBal(bal);
		newacct.setApproved(false);
		newaccts.add(newacct);
		String newDeposit = "Your Initial Deposit is " + newacct.getAcctBal();
		trans.setTransaction(newDeposit);
		date= LocalDate.now();
		adate = date.format(DateTimeFormatter.ofPattern("MMM-dd-yy"));
		trans.setDot(adate);
		System.out.println(trans.getContent());
		transacts.add(trans);
		newacct.setTransactions(transacts);
		Random rng = new Random();
		newacct.setAcctNum(rng.nextInt(999999999));
		uServe.addToDB(newacct,un);
		uServe.addToDB(trans, newacct);
		log.info("User made the choice of " + choice + "and made a new Bank account");
	}
	public void  selectNewUserAcct() throws SQLException
	{
		String username = getUsernameInput();
		String firstName = getUserFirstName();
		String lastName = getUserLastName();
		String pw = getUserPassword();
	   	User newUser = new User(firstName,lastName,username,pw);
		uServe.addToDB(newUser);
		log.info("User made the choice of " + choice + "and made a new login account");
	}
	private String getUsernameInput() {
		System.out.println("What would you like your Username to be:  ");
		String inputs = Menu.input.nextLine().trim();
		
		return inputs;
	}
	private String getUserFirstName() {
		System.out.println("What is your first name:  ");
		String inputs = Menu.input.nextLine().trim();
		
		return inputs;
		}
	private String getUserLastName() {
		System.out.println("What is your Last Name:  ");
		String inputs = Menu.input.nextLine().trim();
		return inputs;
	}
	private String getUserPassword() {
		System.out.println("What is your Password:  ");
		String inputs = Menu.input.nextLine().trim();
		return inputs;
	}
	private String getStartingDeposit() {
		System.out.println("Enter your Starting deposit: ");
		String input = Menu.input.nextLine();
		String input1 = "You Deposited : " + input + " dollars";
		System.out.println(input1);
		return input;
	}
	public void makeAcctNum(Account a,User u) throws SQLException {
		Random rng = new Random();
		a.setAcctNum(rng.nextInt(999999999));
		Account b = preventRepeateAcctNum(c = ConnectionUtil.getConnection(), a, u);
		
		while(a.getAcctNum()== b.getAcctNum())
		{
			a.setAcctNum(rng.nextInt(999999999));
		}
		
	}
public String getAUsernameInput() {
	System.out.println("What is your Existing Username: ");
	String inputs = Menu.input.nextLine().trim();
	
	return inputs;
}
	public Account preventRepeateAcctNum(Connection c, Account a,User u) throws SQLException {
		String sql = "SELECT * FROM \"Banking\".accounts where username = ? ;";
		PreparedStatement pstmt = c.prepareStatement(sql);
		String un = u.getUsername();
		pstmt.setString(1, un);
		ResultSet rs = pstmt.executeQuery();
		Account b = new Account();
		while (rs.next()) {
			int id = rs.getInt("accountNumber");
			String username = rs.getString("username");
			double balance = rs.getDouble("balance");
			boolean status = rs.getBoolean("approved");
			b = new Account(id, username, balance, status);
		}
		return b;
	}
	@Override
	public void display(String un) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}


