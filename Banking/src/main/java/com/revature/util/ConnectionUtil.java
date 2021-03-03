package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.Driver;

public class ConnectionUtil {
	
	public static Connection getConnection() throws SQLException {	
	
		Driver pgDriver = new Driver();
		
		DriverManager.registerDriver(pgDriver);
		
		//where im going to pass the credientals so they are secure and not hard coded in
		String url = System.getenv("db_url"); 
		String username = System.getenv("db_username");
		String password = System.getenv("db_password");
		
		//sending the connection all the variables to connect with
		Connection con = DriverManager.getConnection(url, username, password);
		
		return con;
	}
	
}
