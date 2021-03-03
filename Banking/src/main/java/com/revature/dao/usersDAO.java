package com.revature.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.revature.model.Account;
import com.revature.model.User;

public interface usersDAO {
	
	User getUserByName(String username, Connection con) throws SQLException;

	

	
	
}
