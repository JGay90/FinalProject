package com.revature.model;

import java.util.List;

import com.revature.exceptions.IllegalDepositException;
import com.revature.exceptions.IllegalWithdrawlException;

public class Account {

	private int acctNum;
	private double acctBal;
	private double withdrawl;
	private double deposit;
	private double zellwithdrawl;
	private double zelldepoist;
	private List<Transaction> transactions;
	private boolean isApproved;
	private boolean isClosed;
	private boolean isPending;
	private boolean isAccepted;
	private String username;
	
	
	
	
	
	public Account() {
		super();
	}
	public Account (int acctnum, String un,double bal, boolean status ) {
		this.acctNum = acctnum;
		this.acctBal = bal;
		this.isApproved = status;
		this.username = un;
	}
	
	public Account(int acctnum, double bal, List<Transaction> acts, boolean closed, boolean approved) {
		super();
		this.acctNum = acctnum;
		this.acctBal = bal;
		this.transactions.addAll(acts);
		this.isApproved = approved;
		this.isClosed = closed;	
	}
	
	public double getAcctBal() {
		return acctBal;
	}
	public void setAcctBal(double acctBal) {
		this.acctBal = acctBal;
	}
	public double getWithdrawl() {
		return withdrawl;
	}
	public void setWithdrawl(double withdrawl) {
		this.withdrawl = withdrawl;
	}
	public double getDeposit() {
		return deposit;
	}
	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}
	public double getZellwithdrawl() {
		return zellwithdrawl;
	}
	public void setZellwithdrawl(double zellwithdrawl) {
		this.zellwithdrawl = zellwithdrawl;
	}
	public double getZelldepoist() {
		return zelldepoist;
	}
	public void setZelldepoist(double zelldepoist) {
		this.zelldepoist = zelldepoist;
	}
	public int getAcctNum() {
		return acctNum;
	}
	public void setAcctNum(int acctNum) {
		this.acctNum = acctNum;
	}
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	public boolean isApproved() {
		return isApproved;
	}
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	public boolean isClosed() {
		return isClosed;
	}
	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}
	public boolean isPending() {
		return isPending;
	}
	public void setPending(boolean isPending) {
		this.isPending = isPending;
	}
	public boolean isAccepted() {
		return isAccepted;
	}
	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}
	public Account setDefaults(Account a,double bal) {
		a.setAcctBal(bal);
		a.setApproved(false);
		return a;
	}
	@Override
	public String toString() {
		return "Account Number: "+acctNum + "\r\n";
	}
	
	public void updateBalanceW(double w, double b) throws IllegalWithdrawlException {
		double bal = b;
		double with = w;
		if(with > 0) {
		bal -=with;
		if(bal >= 0)
		{
		setAcctBal(bal);
		System.out.println("New balance is: "+ getAcctBal());
		}
		else
		{
			throw new  IllegalWithdrawlException("Insufficent Funds");
		}
		}
		
	}
	
	public void updateBalanceD(double b,double d) throws IllegalDepositException {
		double bal = b;
		double depos =d;
		if(depos > 0) {
		bal += depos;
		setAcctBal(bal);
		System.out.println("New balance is: "+ getAcctBal());
		}
		else
		{
			throw new IllegalDepositException("Deposits Must be positive");
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((transactions == null) ? 0 : transactions.hashCode());
		result = prime * result + acctNum;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (transactions == null) {
			if (other.transactions != null)
				return false;
		} else if (!transactions.equals(other.transactions))
			return false;
		if (acctNum != other.acctNum)
			return false;
		return true;
	}
	public void updateBalanceZell(double amount, double acctBal2) throws IllegalWithdrawlException {
		double zellwithdrawl = amount;
		double bal = acctBal2;
		bal = bal - zellwithdrawl;
		if(bal >= 0)
		{
		setAcctBal(bal);
		System.out.println("New balance is: "+ getAcctBal());
		setPending(true);
		}
		else
		{
			throw new  IllegalWithdrawlException("Insufficent Funds");
		}
		
		
		
		
		
	}

}
