package com.codehouse.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({ ClienteNotFoundexception.class, ProductNotFoundexception.class,
		FacturaNotFoundexception.class, DetalleFacturaNotFoundexception.class })
	public ResponseEntity<Object> handleClienteNotFoundException(Exception ex) {
		Map<String, Object> response = new HashMap<>();
		response.put("timestamp", LocalDateTime.now());
		response.put("status", HttpStatus.NOT_FOUND.value());
		response.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
		response.put("message", ex.getMessage());
		

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGenericException(Exception ex) {
		Map<String, Object> response = new HashMap<>();
		response.put("timestamp", LocalDateTime.now());
		response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		response.put("message", ex.getMessage());
		response.put("path", "/api/clientes/{id}");

		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
