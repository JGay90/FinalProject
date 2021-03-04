package com.revature.ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.dao.usersDAOImp;
import com.revature.exceptions.IllegalDepositException;
import com.revature.exceptions.IllegalWithdrawlException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.model.Account;
import com.revature.model.Transaction;
import com.revature.model.User;
import com.revature.services.UserService;
import com.revature.util.ConnectionUtil;

public class ZellMenu implements Menu {
	public UserService userService = new UserService();
	public LocalDate date;
	public String un;
	public String pw;
	public int i;
	public List<Transaction>transacts = new ArrayList<Transaction>();
	public List<Account>accts = new ArrayList<Account>();
	public Connection con;
	User user = new User();
	Transaction trans = new Transaction();
	Account a = new Account();
	Account b = new Account();
	String adate;
	usersDAOImp udao = new usersDAOImp();
	int z = 1;
	int choice;
	private static Logger log = Logger.getLogger(ZellMenu.class);
	@Override
	public void display() throws SQLException {
		choice = 0;
		con = ConnectionUtil.getConnection();
		do {
			System.out.println("=== Zell MENU ===");
			System.out.println("Please select an option below: ");
			System.out.println("1.) Back");
			System.out.println("2.) Check Account");
			System.out.println("3.) Get Pending Transactions");
			System.out.println("4.) Send Money");
			
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
				try {
					getPendingTransactions(getAcctNum());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalDepositException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
		
			case 4:
				//Send a Transaction
				break;
				//logic here to pull up accounts.
			default:
				System.out.println("No valid choice entered, please try again");
		
	} 
	}while(choice != 1);

}
public void getPendingTransactions(int num) throws SQLException, IllegalDepositException {
getAllZellTransactions(con, num);
System.out.println("Would you like to Accept the Zell : y/n");
 String input = Menu.input.nextLine().trim();
 String affirm = "y";
 while(! input.equalsIgnoreCase(affirm)|| input.equalsIgnoreCase("yes") ||input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")){
		 if(input.equalsIgnoreCase(affirm)|| input.equalsIgnoreCase("yes"))
 {
	 updateZellDB(con, num,a.getZelldepoist());
 }else {
	 if(input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no"))
	 {
	 updateZellDenyDB(con,num,a.getZelldepoist());
	 }
	 else
	 {
		 System.out.println("Would you like to Accept the Zell : y/n");
		 input = Menu.input.nextLine().trim();
	 }
 }
 }
	
}
public String getAUsernameInput() {
	System.out.println("For Security what is your Username: ");
	String inputs = Menu.input.nextLine().trim();

	return inputs;
}
private int getAcctNum() {
	System.out.println("Enter the Account Number: ");
	int input = Integer.parseInt(Menu.input.nextLine().trim());

	return input;
}
public void selectCheckAccounts() {
	user.setUsername(getAUsernameInput());
	try {
		
		user = userService.getUserByUsername(user.getUsername().toString());
		accts = udao.getAcctByUserwithBalance(user.getUsername());
		user.setAccounts(accts);
		for(z= 0; z < user.getAccounts().size(); z++)
		{
			printAcctBals();
		}
		
		
	} catch (SQLException | UserNotFoundException e) {
		System.out.println(e.getClass().getSimpleName() + " " + e.getMessage());
	}
	
}
public void printAcctBals() {
	a = new Account();
	a.setAcctNum(accts.get(z).getAcctNum());
	a.setAcctBal(accts.get(z).getAcctBal());
	System.out.println(a.getAcctNum() +" Your balance is : "+ a.getAcctBal());
}
private String getZellWithdrawl(int i) {
	System.out.println("Please enter the Account you wish to send to: ");
	String acctnum = Menu.input.nextLine().trim();
	int bacct = Integer.parseInt(acctnum);
	b.setAcctNum(bacct);
	
	System.out.println("Enter the amount you wish to Send: ");
	String input = Menu.input.nextLine().trim();
    double ab = Double.parseDouble(input);
    
    trans.setAmount(ab);
    trans.setAcctNum(i);
    a.setZellwithdrawl(ab);
	input = "You sent : " + input + " dollars From Account: " + trans.getAcctNum() + " To Account " + b.getAcctNum();
	trans.setTransaction(input);
	
	return input;
}
public ArrayList<Account> getAllZellTransactions(Connection con,int acctnum) throws SQLException {
ArrayList<Account> user = new ArrayList<Account>();
String sql = "SELECT moneysent,sendingacct  FROM \"Banking\".zell where pending = true  and recievingacct = ?;";
PreparedStatement pstmt = con.prepareStatement(sql);
pstmt.setInt(1, acctnum);
ResultSet rs = pstmt.executeQuery();
while (rs.next()) {
	a = new Account();
	b = new Account();
	a.setAcctNum(acctnum);
	a.setZelldepoist(rs.getDouble("moneysent"));
	b.setAcctNum(rs.getInt("sendingacct"));
	b.setZellwithdrawl(a.getZelldepoist());
	updateZellDB(con,a.getAcctNum(),a.getZelldepoist());
	userService.addZellUpdateDBdeposit(a);
	
	user.add(a);
	user.add(b);
}
System.out.println("You have a Zell pending for  "+ a.getZelldepoist() + " from " + b.getAcctNum());
return user;
}

public void updateZellDB(Connection con, int acct,double amount) throws SQLException {
	String sql = "UPDATE \"Banking\".zell SET accepted =true, pending = false WHERE recievingacct= ? and moneysent = ?;";
	PreparedStatement pstmt = con.prepareStatement(sql);
	pstmt.setInt(1, acct);
	pstmt.setDouble(2, amount);
	pstmt.executeUpdate();
	
}

public void updateZellDenyDB(Connection con, int acct,double amount) throws SQLException, IllegalDepositException {
	String sql = "UPDATE \"Banking\".zell SET denied =true, pending = false WHERE recievingacct= ? and moneysent = ?;";
	PreparedStatement pstmt = con.prepareStatement(sql);
	pstmt.setInt(1, acct);
	pstmt.setDouble(2, amount);
	pstmt.executeUpdate();
	b.setDeposit(amount);
	b = userService.getAccountBak(b.getAcctNum(), a);
	b.updateBalanceD(b.getAcctBal(), amount);
}

public void sendZell(int a, int b) {
	//logic
	
}
}
