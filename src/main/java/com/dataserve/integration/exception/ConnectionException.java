package com.dataserve.integration.exception;

public class ConnectionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5892154978860173772L;
	public static final int FILENET_ACCESS_ERROR = 512;
	public static final int OBJECT_STORE_ACCESS_ERROR = 513;
	
	private int errorCode;

	public ConnectionException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public ConnectionException(int errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}
	
	public int getErrorCode() {
		return errorCode;
	}

}
