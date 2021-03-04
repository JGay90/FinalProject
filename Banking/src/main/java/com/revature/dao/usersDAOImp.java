package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.Account;
import com.revature.model.Transaction;
import com.revature.model.User;
import com.revature.services.UserService;
import com.revature.util.ConnectionUtil;

public class usersDAOImp implements usersDAO {

	public User getUserByName(String username, Connection con) throws SQLException {
		User user = null;

		String sql = "SELECT * FROM \"Banking\".users WHERE username = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);

		pstmt.setString(1, username);

		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {
			String un = rs.getString("username");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			String pw = rs.getString("passwords");

			user = new User(firstName, lastName,un, pw);
		}

		return user;
	}
	
	
	
	public List<Account> getAcctByUser(String un) throws SQLException {
		Connection con = ConnectionUtil.getConnection();
		Account a = new Account();
		List<Account>accts = new ArrayList<Account>();
		String sql = "Select accountnumber from \"Banking\".accounts where username = ?";
		PreparedStatement myStmt = con.prepareStatement(sql);
		
		myStmt.setString(1, un);
		ResultSet rs = myStmt.executeQuery();
		while(rs.next())
		{
			a = new Account();
			a.setAcctNum(rs.getInt("accountnumber"));
			accts.add(a);
			
		}
		return accts;
	}
	public List<Account> getAcctByUserwithBalance(String un) throws SQLException {
		Connection con = ConnectionUtil.getConnection();
		Account a = new Account();
		List<Account>accts = new ArrayList<Account>();
		String sql = "Select accountnumber,balance from \"Banking\".accounts where username = ?";
		PreparedStatement myStmt = con.prepareStatement(sql);
		
		myStmt.setString(1, un);
		ResultSet rs = myStmt.executeQuery();
		while(rs.next())
		{
			a = new Account();
			a.setAcctNum(rs.getInt("accountnumber"));
			a.setAcctBal(rs.getDouble("balance"));
			accts.add(a);
			
		}
		return accts;
	}
	
	public List<Transaction> getTransactionsByAcct(String un) throws SQLException {
		Connection con = ConnectionUtil.getConnection();
		Transaction t = new Transaction();
		List<Transaction>trans = new ArrayList<Transaction>();
		String sql = "Select * from \"Banking\".transaction where accountnumber = ?";
		PreparedStatement myStmt = con.prepareStatement(sql);
		Account a = new Account();
		a = getAcctByUn(un);
		myStmt.setInt(1,a.getAcctNum());
		ResultSet rs = myStmt.executeQuery();
		while(rs.next())
		{
			t = new Transaction();
			t.setTransaction(rs.getString("transaction_type"));
			t.setDot(rs.getString("date of transaction"));
			t.setAcctNum(rs.getInt("accountnumber"));
			trans.add(t);
			
		}
		return trans;
	}
	
	public List<Transaction> getTransactionsByAcct(int i) throws SQLException {
		Connection con = ConnectionUtil.getConnection();
		Transaction t = new Transaction();
		List<Transaction>trans = new ArrayList<Transaction>();
		String sql = "Select * from \"Banking\".transaction where accountnumber = ?";
		PreparedStatement myStmt = con.prepareStatement(sql);
		Account a = new Account();
		a.setAcctNum(i);	
		myStmt.setInt(1,a.getAcctNum());
		ResultSet rs = myStmt.executeQuery();
		while(rs.next())
		{
			t = new Transaction();
			t.setTransaction(rs.getString("transaction_type"));
			t.setDot(rs.getString("date of transaction"));
			t.setAcctNum(rs.getInt("accountnumber"));
			trans.add(t);
			
		}
		return trans;
	}
	private Account getAcctByUn(String un) throws SQLException {
		Connection con = ConnectionUtil.getConnection();
		Account a = new Account();
		
		String sql = "Select accountnumber from \"Banking\".accounts where username = ?";
		PreparedStatement myStmt = con.prepareStatement(sql);
		
		myStmt.setString(1, un);
		ResultSet rs = myStmt.executeQuery();
		while(rs.next())
		{
			
			a.setAcctNum(rs.getInt("accountnumber"));
			
		}
		return a;
	}



	public Boolean logIn(String un, String pw, Connection con) throws SQLException {
		User u = new User();
		Boolean wasFound = false;
		String sql = "SELECT * FROM \"Banking\".users WHERE username = ? AND passwords =?";
		PreparedStatement pstmt = con.prepareStatement(sql);

		pstmt.setString(1, un);
		pstmt.setString(2, pw);
		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {
			String un1 = rs.getString("username");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			String pw1 = rs.getString("passwords");
			
			u.setFirstName(firstName);
			u.setLastName(lastName);
			u.setPw(pw1);
			u.setUsername(un1);
			wasFound = true;
			}
			
		return wasFound;
	}
	
	public Boolean employeelogIn(String un, String pw, Connection con) throws SQLException {
		User u = new User();
		Boolean wasFound = false;
		String sql = "SELECT * FROM \"Banking\".employee WHERE username = ? AND password =?";
		PreparedStatement pstmt = con.prepareStatement(sql);

		pstmt.setString(1, un);
		pstmt.setString(2, pw);
		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {
			String un1 = rs.getString("username");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			String pw1 = rs.getString("password");
			
			u.setFirstName(firstName);
			u.setLastName(lastName);
			u.setPw(pw1);
			u.setUsername(un1);
			wasFound = true;
			}
			
		return wasFound;
	}
	public List<Transaction> gettransactions() throws SQLException{
		List<Transaction>trans = new ArrayList<Transaction>();
		Connection con = ConnectionUtil.getConnection();
		Transaction t;
		
		String sql = "SELECT * FROM \"Banking\".transaction";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			t = new Transaction();
			t.setTransaction(rs.getString("transaction_type"));
			t.setDot(rs.getString("date of transaction"));
			t.setAcctNum(rs.getInt("accountnumber"));
			trans.add(t);
		
		}
		
		return trans;
		
	}
}
	
