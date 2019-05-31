package com.miracle.effort.exception;

import org.springframework.http.HttpStatus;

import com.miracle.exception.GatewayServiceException;

public class EffortException extends GatewayServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4633876870673211026L;
	
	/**
	 * @param message
	 */
	public EffortException(String message) {
		super(message);
	}
	
	public EffortException(String message,String errorCode) {
		super(message);
		setErrorCode(errorCode);
	}
	public EffortException(String message,String errorCode,HttpStatus statusCode) {
		super(message);
		setErrorCode(errorCode);
		setStatusCode(statusCode);
	}

	/**
	 * @param cause
	 */
	public EffortException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public EffortException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public EffortException(String message, Throwable cause,String errorCode) {
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
	public EffortException(String message, Throwable cause,String errorCode,HttpStatus statusCode) {
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
	public EffortException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
