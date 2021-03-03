package com.revature.ui;

import java.sql.SQLException;
import java.util.Scanner;

import com.revature.exceptions.UserNotFoundException;

public interface Menu {

	//Scanner that will be used to get input
	public static final Scanner input = new Scanner(System.in);
	
	void display() throws SQLException; //abstract display method
	
	void display(String un) throws SQLException;
	
}
