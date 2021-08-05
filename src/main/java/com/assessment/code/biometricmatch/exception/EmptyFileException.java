package com.assessment.code.biometricmatch.exception;

public class EmptyFileException extends RuntimeException {
	private static final long serialVersionUID = 18L;
	public EmptyFileException(String message){ 
		super(message); 
	}
}
