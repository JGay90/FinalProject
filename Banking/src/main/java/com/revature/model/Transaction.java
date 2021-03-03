package com.revature.model;

import java.sql.Date;
import java.time.LocalDateTime;

public class Transaction {
	private String transaction; //What happened in the Transaction
	private String dot; //Date of Transaction
	public double amount;
	private int acctNum;
	
	
	
	public Transaction( String transaction, String date) {
		super();
	
		this.transaction = transaction;
		this.setDot(date);
	}
	
	public Transaction() {
		super();
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double a) {
		this.amount= a;
	}
	
	public String getContent() {
		return transaction;
	}
	
	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}
	
	@Override
	public String toString() {
		
		return "Transaction Date: "+ dot + " : Acct#:  " +acctNum +"  Transaction : " +transaction+ "\r\n"; 
	}

	public String getDot() {
		return dot;
	}

	public void setDot(String i) {
		this.dot = i;
	}

	public int getAcctNum() {
		return acctNum;
	}

	public void setAcctNum(int acctNum) {
		this.acctNum = acctNum;
	}


}
