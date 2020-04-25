package com.ecommerce.microservices.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundProductException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7918929387686380106L;

	public NotFoundProductException(String message) {
		super(message);
	}
}
