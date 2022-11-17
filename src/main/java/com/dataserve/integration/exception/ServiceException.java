package com.dataserve.integration.exception;

public class ServiceException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int errorCode;
	

	public ServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public ServiceException(int errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
