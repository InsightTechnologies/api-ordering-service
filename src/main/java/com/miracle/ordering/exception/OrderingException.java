package com.miracle.ordering.exception;

import org.springframework.http.HttpStatus;

import com.miracle.exception.GatewayServiceException;

public class OrderingException extends GatewayServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -816387637206481014L;
	public OrderingException() {
	}

	/**
	 * @param message
	 */
	public OrderingException(String message) {
		super(message);
	}
	
	public OrderingException(String message,String errorCode) {
		super(message);
		setErrorCode(errorCode);
	}
	public OrderingException(String message,String errorCode,HttpStatus statusCode) {
		super(message);
		setErrorCode(errorCode);
		setStatusCode(statusCode);
	}

	/**
	 * @param cause
	 */
	public OrderingException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public OrderingException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * 
	 * @param message
	 * @param cause
	 * @param errorCode
	 */
	public OrderingException(String message, Throwable cause,String errorCode) {
		super(message, cause);
		setErrorCode(errorCode);
	}
	
	/**
	 * 
	 * @param message
	 * @param cause
	 * @param errorCode
	 * @param statusCode
	 */
	public OrderingException(String message, Throwable cause,String errorCode,HttpStatus statusCode) {
		super(message, cause);
		setErrorCode(errorCode);
		setStatusCode(statusCode);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public OrderingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
