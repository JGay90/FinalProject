package com.revature.ui;

import org.apache.log4j.Logger;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//import org.apache.log4j.Logger;

import com.revature.dao.*;
import com.revature.exceptions.IllegalDepositException;
import com.revature.exceptions.IllegalWithdrawlException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.model.Account;
import com.revature.model.Transaction;
import com.revature.model.User;
import com.revature.services.UserService;

public class UserMenu implements Menu {
	public UserService userService;
	public LocalDate date;
	public String un;
	public String pw;
	public int i;
	public List<Transaction>transacts = new ArrayList<Transaction>();
	public List<Account>accts = new ArrayList<Account>();
	User user = new User();
	Transaction trans = new Transaction();
	Account a = new Account();
	Account b = new Account();
	String adate;
	usersDAOImp udao = new usersDAOImp();
	int z = 1;
	int choice;
	private static Logger log = Logger.getLogger(UserMenu.class);
	
	public UserMenu(String uname) {
		this.userService = new UserService();
		un = uname;
	}
	
	@SuppressWarnings({ "deprecation", "static-access" })
	public void display() throws SQLException {
		choice = 0;
		
		do {
			System.out.println("=== USER MENU ===");
			System.out.println("Please select an option below: ");
			System.out.println("1.) Back");
			System.out.println("2.) Check Accounts");
			System.out.println("3.) See Transactions");
			System.out.println("4.) Send Money");
			System.out.println("5.) Withdrawl");
			System.out.println("6.) Depoist");
			
			try {
				choice = Integer.parseInt(Menu.input.nextLine());
			} catch (NumberFormatException e) {
			}
			
			switch (choice) {
				case 1:
					break;
				case 2:
					selectCheckAccounts();
					break;
				case 3:
					selectTransHistory();
					break;
			
				case 4:
					System.out.println("Zell Menu Here");
					break;
					//logic here to pull up accounts.
				case 5:
					
					selectWithdrawls();
					break;
				case 6:
					//Some reason my AcctNums are not loading in right
					selectDeposit();
					break;
					
				default:
					System.out.println("No valid choice entered, please try again");
			}
			
		} while(choice != 1);
	}
	

	private String getUsernameInput() {
		System.out.println("Enter a username that you would like to lookup: ");
		String input = Menu.input.nextLine().trim();
		
		return input;
	}
	private String getDeposit() {
		System.out.println("Enter your deposit: ");
		String input = Menu.input.nextLine().trim();
		 double a = Double.parseDouble(input);
		 trans.setAmount(a);
		input = "You Deposited : " + input + " dollars";
		return input;
	}
	private String getWithdrawl() {
		System.out.println("Enter the amount you wish to Withdrawl: ");
		String input = Menu.input.nextLine().trim();
	    double a = Double.parseDouble(input);
	    trans.setAmount(a);
	  //  System.out.println("transaction inside trans object " + trans.getAmount());
		input = "You Withdrew : " + input + " dollars";
		return input;
	}
	private String getWithdrawl(int i) {
		System.out.println("Enter the amount you wish to Withdrawl: ");
		String input = Menu.input.nextLine().trim();
	    double a = Double.parseDouble(input);
	    trans.setAmount(a);
	    trans.setAcctNum(i);
		input = "You Withdrew : " + input + " dollars From Account: " + trans.getAcctNum();
		return input;
	}
	
	private String getDeposit(int i) {
		System.out.println("Enter your deposit: ");
		String input = Menu.input.nextLine().trim();
		 double a = Double.parseDouble(input);
		 trans.setAmount(a);
		 trans.setAcctNum(i);
		input = "You Deposited : " + input + " dollars";
		return input;
	}
	private int getAcctNum() {
		System.out.println("Enter the Account Number: ");
		int input = Integer.parseInt(Menu.input.nextLine().trim());

		return input;
	}
	
public void printAcctBals() {
	a = new Account();
	a.setAcctNum(accts.get(z).getAcctNum());
	a.setAcctBal(accts.get(z).getAcctBal());
	System.out.println(a.getAcctNum() +" Your balance is : "+ a.getAcctBal());
}

@Override
public void display(String un) throws SQLException {
	// TODO Auto-generated method stub
	
} 
public void selectTransHistory() {
	try {
		accts = udao.getAcctByUser(un);
		user.setAccounts(accts);
		
		if(user.getAccounts().size()>1)
		{
			System.out.println("Which Account would you like to View: ");
			User user = userService.getUserByUsername(un);
			user.setAccounts(udao.getAcctByUser(un));
			System.out.println(user.getAccounts().toString());
			i = getAcctNum();
			transacts = udao.getTransactionsByAcct(i);
			System.out.println(transacts.toString());
			
		}
		
		else {
			User user = userService.getUserByUsername(un);
			user.setAccounts(udao.getAcctByUser(un));
			transacts = udao.getTransactionsByAcct(un);
			System.out.println(transacts.toString());	
		}
		
	} catch (SQLException | UserNotFoundException e) {
		System.out.println(e.getClass().getSimpleName() + " " + e.getMessage());
	}
	log.info("User made the choice of " + choice + "and recieved their Transaction history");
}
	public void selectDeposit() throws SQLException {
	accts = udao.getAcctByUser(un);
	user.setAccounts(accts);
	user.setUsername(un);
	if(user.getAccounts().size()>1)
	{
		System.out.println("Which Account would you like to View: ");
		try {
			user = userService.getUserByUsername(un);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		user.setAccounts(udao.getAcctByUserwithBalance(un));
		System.out.println(user.getAccounts().toString());
		i = getAcctNum();
		
		userService.getName(un,user);
		trans.setTransaction(getDeposit(i));
		date= LocalDate.now();
		adate = date.format(DateTimeFormatter.ofPattern("MMM-dd-yy"));
		trans.setDot(adate);
		trans.setAcctNum(i);
		System.out.println(trans.getContent());
		transacts.add(trans);
		user.setTransactions(transacts);
		a.setAcctNum(i);
		a.setDeposit(trans.getAmount());
		int x= 0;
		while (x < accts.size())
		{
			if(a.getAcctBal() == accts.get(x).getAcctBal())
			{
				a.setAcctBal(accts.get(x).getAcctBal());
			}
			else {
				x++;
			}
		}
		
		//userService.getAccountNum(un, a);
		try {
			a.updateBalanceD(a.getAcctBal(),a.getDeposit());
			a.getAcctBal();
		} catch (IllegalDepositException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.warn("User made the choice of " + choice + "and put in an illegal deposit");
		}
		userService.addToDB(trans, user, a);
		userService.addUpdateDB(a, un);
	}else {
	user.setUsername(un);
	userService.getName(un,user);
	trans.setTransaction(getDeposit());
	date= LocalDate.now();
	adate = date.format(DateTimeFormatter.ofPattern("MMM-dd-yy"));
	trans.setDot(adate);
	System.out.println(trans.getContent());
	transacts.add(trans);
	user.setTransactions(transacts);
	userService.getAccountNum(un, a);
	a.setDeposit(trans.getAmount());
	int x= 0;
	while (x < accts.size())
	{
		if(a.getAcctBal() == accts.get(x).getAcctBal())
		{
			a.setAcctBal(accts.get(x).getAcctBal());
		}
		else {
			x++;
		}
	}
	
	//userService.getAccountNum(un, a);
	try {
		a.updateBalanceD(a.getAcctBal(),a.getDeposit());
		a.getAcctBal();
	} catch (IllegalDepositException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	userService.addToDB(trans, user, a);
	userService.addUpdateDB(a, un);
		}
	log.info("User made the choice of " + choice + "and made a deposit");
}
public void selectCheckAccounts() {
	try {
		User user = userService.getUserByUsername(un);
		accts = udao.getAcctByUserwithBalance(un);
		user.setAccounts(accts);
		for(z= 0; z < user.getAccounts().size(); z++)
		{
			printAcctBals();
		}
		
		
	} catch (SQLException | UserNotFoundException e) {
		System.out.println(e.getClass().getSimpleName() + " " + e.getMessage());
	}
	
}

public void selectWithdrawls() throws SQLException {
	accts = udao.getAcctByUser(un);
	user.setAccounts(accts);
	
	if(user.getAccounts().size()>1)
	{
		System.out.println("Which Account would you like to View: ");
		try {
			user = userService.getUserByUsername(un);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user.setAccounts(udao.getAcctByUserwithBalance(un));
		System.out.println(user.getAccounts().toString());
		i = getAcctNum();
		
		trans.setTransaction(getWithdrawl());
		date= LocalDate.now();
		adate = date.format(DateTimeFormatter.ofPattern("MMM-dd-yy"));
		trans.setDot(adate);
		//System.out.println(trans.getDot());
	//	System.out.println(trans.getContent());
		trans.setAcctNum(i);
		transacts.add(trans);
		a.setAcctNum(i);
		int x= 0;
		while (x < accts.size())
		{
			if(a.getAcctBal() == accts.get(x).getAcctBal())
			{
				a.setAcctBal(accts.get(x).getAcctBal());
			}
			else {
				x++;
			}
		}
		try {
			a.updateBalanceW(trans.getAmount(), a.getAcctBal());
			a.getAcctBal();
			} catch (IllegalWithdrawlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.warn("User made the choice of " + choice + "and put in an illegal withdrawl");
		}
		user.setTransactions(transacts);
		user.getTransactions();
		userService.addToDB(trans,user, a);
		userService.addUpdateDB(a, un);
	}
	else {
		user.setUsername(un);
		userService.getName(un,user);
		trans.setTransaction(getWithdrawl());
		date= LocalDate.now();
		adate = date.format(DateTimeFormatter.ofPattern("MMM-dd-yy"));
		trans.setDot(adate);
		//System.out.println(trans.getDot());
		//System.out.println(trans.getContent());
		transacts.add(trans);
		user.setTransactions(transacts);
		user.getTransactions();
		userService.getAccountNum(un, a);
		userService.addToDB(trans, user, a);
		userService.addUpdateDB(a, un);
	}
	log.info("User made the choice of " + choice + "and made a withdrawl");
	
}
}

