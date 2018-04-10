package com.gspann.itrack.common.exception;

/**
 * The exception class to be extended by all custom Exception classes
 * 
 * @author Y Kamesh Rao
 */
public class BaseException extends Exception {

	private static final long serialVersionUID = -1361793725840005644L;

	// To be set by exception throwing class
	protected Object errorCode;

	public BaseException(Object errorCode) {
		this.errorCode = errorCode;
	}

	public BaseException(Object errorCode, Throwable cause) {
		super(cause);
	}

	public BaseException(Object errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public BaseException(Object errorCode, String message, Throwable cause) {
		super(message, cause);
	}

	public Object getErrorCode() {
		return errorCode;
	}
}
