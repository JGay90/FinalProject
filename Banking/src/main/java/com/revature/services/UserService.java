package com.revature.services;

import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.dao.usersDAO;
import com.revature.dao.usersDAOImp;
import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.model.Account;
import com.revature.model.Transaction;
import com.revature.model.User;
import com.revature.util.ConnectionUtil;

public class UserService {

	public usersDAO udao;
	private static Logger log = Logger.getLogger(UserService.class);
	public UserService() {
		this.udao = new usersDAOImp();
		
	}
	
	public User getUserByUsername(String username) throws UserNotFoundException, SQLException {
		try (Connection con = ConnectionUtil.getConnection()) {
			User user;
			
			user = udao.getUserByName(username, con) ;
			
			if (user == null) {
				throw new UserNotFoundException("User with username '" + username + "' was not found!");
			//	log.warn("Program tried to find a User but Username was not found in Database");
			}
			log.info("Program found user in Database");
//			else {
//				
//				user.getAccounts();
//				user.getFirstName();
//				user.getLastName();
//				user.getId();
//				user.getTransactions();
	//		}
			
			return user;
		}
	}
	
	
	
		
	public void testConnection() throws SQLException {
		try (Connection con = ConnectionUtil.getConnection()) {
			
			System.out.println(con);
			
			if (con == null) {
				System.out.println("Connection not made");
			}
		
	}
		
	}
	public void addToDB(User u) throws SQLException {
		User user = u;
		Connection con = ConnectionUtil.getConnection();
		
		String sql = "INSERT INTO \"Banking\".users (username,first_name,last_name,passwords)" +
				"VALUES(?,?,?,?);";
		PreparedStatement myStmt = con.prepareStatement(sql);
		
		myStmt.setString(1, user.getUsername());
		myStmt.setString(2, user.getFirstName());
		myStmt.setString(3, user.getLastName());
		myStmt.setString(4, user.getPw().toString());
		int res = myStmt.executeUpdate();
		 // Display the records inserted 
        System.out.println(res + " records inserted"); 
        log.info("Program added a User to Database");
		
      		
	}
	public int getUserID(Connection c, User u) throws SQLException
	{
		int i;
		Connection con = c;
		String un = u.getUsername();
		u = udao.getUserByName(un, con);
		i = u.getId();
		return i;
	}
	public void addToDB(Account a,User u) throws SQLException {
		User user = u;
		Connection con = ConnectionUtil.getConnection();
		
		String sql = "INSERT INTO \"Banking\".accounts (accountnumber, username, balance,approved,id)" +
				"VALUES(?,?,?,?,?);";
		PreparedStatement myStmt = con.prepareStatement(sql);
		user.setId(getUserID(con, user));
		myStmt.setInt(1, a.getAcctNum());
		myStmt.setString(2, user.getUsername());
		myStmt.setDouble(3, a.getAcctBal());
		myStmt.setBoolean(4, a.isApproved());
		myStmt.setInt(5,user.getId());
		int res = myStmt.executeUpdate();
		 // Display the records inserted 
        System.out.println(res + " account record inserted"); 
        log.info("Program added account to Database");
		
      		
	}
	public void addToDB(Transaction t, Account a) throws SQLException {		
		Connection con = ConnectionUtil.getConnection();
		
		String sql = "INSERT INTO \"Banking\".\"transaction\"\r\n"
				+ "(accountnumber, transaction_type, transactionamount, \"date of transaction\")\r\n"
				+ "VALUES(?, ?, ?,?);";
		PreparedStatement myStmt = con.prepareStatement(sql);
		 
	//	a.setAcctNum(getAccountNum(u.getUsername(),a));
		myStmt.setInt(1,a.getAcctNum());
		myStmt.setString(2, t.getContent());
		myStmt.setDouble(3, t.getAmount());
		myStmt.setString(4, t.getDot());
		int res = myStmt.executeUpdate();
		 // Display the records inserted 
       System.out.println(res + " transaction record inserted"); 
       log.info("Program added a transaction to Database");
		
	}
	public void addToDB(Transaction t,User u, Account a) throws SQLException {		
		Connection con = ConnectionUtil.getConnection();
		
		String sql = "INSERT INTO \"Banking\".\"transaction\"\r\n"
				+ "(accountnumber, transaction_type, transactionamount, \"date of transaction\")\r\n"
				+ "VALUES(?, ?, ?,?);";
		PreparedStatement myStmt = con.prepareStatement(sql);
		 
	//	a.setAcctNum(getAccountNum(u.getUsername(),a));
		myStmt.setInt(1,a.getAcctNum());
		myStmt.setString(2, t.getContent());
		myStmt.setDouble(3, t.getAmount());
		myStmt.setString(4, t.getDot());
		int res = myStmt.executeUpdate();
		 // Display the records inserted 
       System.out.println(res + " transaction record inserted"); 
       log.info("Program added a transaction to Database");
		
	}
	public User getName(String Username,User us) throws SQLException {
		User u = us;
		Connection con = ConnectionUtil.getConnection();
		
		String sql = "Select first_name, last_name from \"Banking\".users where username = ?";
		PreparedStatement myStmt = con.prepareStatement(sql);
		
		myStmt.setString(1, Username);
		String name = myStmt.executeQuery().toString();
		u.setFirstName(name);
		return u;
		
	}
	public Account getAccountNum(String username, Account a) throws SQLException {
		Connection con = ConnectionUtil.getConnection();
		
		String sql = "Select accountnumber from \"Banking\".accounts where username = ? and approved = true";
		PreparedStatement myStmt = con.prepareStatement(sql);
		
		myStmt.setString(1, username);
		ResultSet rs = myStmt.executeQuery();
		while(rs.next())
		{
			a.setAcctNum(rs.getInt("accountnumber"));
			
		}
		//System.out.println(rs);
	//	a.setAcctNum();
		return a;
	}

	public void addToDB(Account a,String un) throws SQLException {
Connection con = ConnectionUtil.getConnection();
		
		String sql = "INSERT INTO \"Banking\".accounts (accountnumber, username, balance,approved)" +
				"VALUES(?,?,?,?);";
		PreparedStatement myStmt = con.prepareStatement(sql);
		
		myStmt.setInt(1, a.getAcctNum());
		myStmt.setString(2,un);
		myStmt.setDouble(3, a.getAcctBal());
		myStmt.setBoolean(4, a.isApproved());
		int res = myStmt.executeUpdate();
		 // Display the records inserted 
        System.out.println(res + " account record inserted");
        log.info("Program added an Account to Database");
		
	}

	public void addUpdateDB(Account a, String un) throws SQLException {
		Connection con = ConnectionUtil.getConnection();
		String sql = "UPDATE \"Banking\".accounts\r\n"
				+ "SET balance=?"
				+ "WHERE accountnumber=?;";
		PreparedStatement myStmt = con.prepareStatement(sql);
		
		myStmt.setDouble(1, a.getAcctBal());
		myStmt.setInt(2, a.getAcctNum());
		int res = myStmt.executeUpdate();
		 System.out.println(res + " account record updated");
		 log.info("Program updated an Account to Database");
	}

	
}



