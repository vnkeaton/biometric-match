package com.assessment.code.biometricmatch.exception;

public class DatabaseConstraintException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public DatabaseConstraintException(String message) {
		super(message);
	}

}
