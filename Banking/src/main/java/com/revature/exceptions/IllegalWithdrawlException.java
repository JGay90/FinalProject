package com.revature.exceptions;

public class IllegalWithdrawlException extends Exception{
	public IllegalWithdrawlException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IllegalWithdrawlException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalWithdrawlException(String message) {
		super(message);
	}

	public IllegalWithdrawlException(Throwable cause) {
		super(cause);
	}
}
