package com.revature.exceptions;

import org.apache.log4j.Logger;

public class UserNotFoundException extends Exception {
	private static Logger log = Logger.getLogger(UserNotFoundException.class);
		public UserNotFoundException() {
			super();
		}

		public UserNotFoundException(String message, Throwable cause, boolean enableSuppression,
				boolean writableStackTrace) {
			super(message, cause, enableSuppression, writableStackTrace);
			log.warn("Program tried to find a User but Username was not found in Database");
		}

		public UserNotFoundException(String message, Throwable cause) {
			super(message, cause);
			log.warn("Program tried to find a User but Username was not found in Database");
		}

		public UserNotFoundException(String message) {
			super(message);
			log.warn("Program tried to find a User but Username was not found in Database");
		}

		public UserNotFoundException(Throwable cause) {
			super(cause);
			log.warn("Program tried to find a User but Username was not found in Database");
		}
	
	}
