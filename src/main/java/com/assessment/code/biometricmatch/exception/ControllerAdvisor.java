package com.assessment.code.biometricmatch.exception;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerAdvisor {
	
	@ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Object> handleFileNotFoundException(
    	        FileNotFoundException ex, WebRequest request) {
		return createExceptionBody(ex.getMessage());
    }
	
	@ExceptionHandler(FileStorageException.class)
    public ResponseEntity<Object> handleFileStorageException(
    	          FileStorageException ex, WebRequest request) {
        return createExceptionBody(ex.getMessage());
    }
	
	@ExceptionHandler(EmptyFileException.class)
    public ResponseEntity<Object> handleEmptyFileException(
    		      EmptyFileException ex, WebRequest request) {
        return createExceptionBody(ex.getMessage());
    }
	
	@ExceptionHandler(DatabaseConstraintException.class)
    public ResponseEntity<Object> handleDatabaseConstraintException(
    		      DatabaseConstraintException ex, WebRequest request) {
        return createExceptionBody(ex.getMessage());
    }

	private ResponseEntity<Object> createExceptionBody(String message) {
		Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", message);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

}
