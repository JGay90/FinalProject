package com.revature.exceptions;

public class IllegalDepositException  extends Exception{
	
public IllegalDepositException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

public IllegalDepositException(String message, Throwable cause) {
		super(message, cause);
}


public IllegalDepositException(String message) {
		super(message);
	}

public IllegalDepositException(Throwable cause) {
		super(cause);
	}
}