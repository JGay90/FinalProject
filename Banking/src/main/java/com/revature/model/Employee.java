package com.revature.model;

import java.util.Date;

public class Employee {
	private String userName;
	private String firstName;
	private String lastName;
	private int eID;
	private Date hireDate;
	private String pw;

	public Employee() {
		super();
	}
	public Employee(String un, String fName, String lName, int id, Date hire, String pw) {
		super();
		this.userName = un;
		this.firstName = fName;
		this.lastName = lName;
		this.seteID(id);
		this.setHireDate(hire);
		this.setPw(pw);

	}


	public String getUsername() {
		return userName;
	}

	public void setUsername(String username) {
		this.userName = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int geteID() {
		return eID;
	}
	public void seteID(int eID) {
		this.eID = eID;
	}
	public Date getHireDate() {
		return hireDate;
	}
	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}

}
