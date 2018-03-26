package com.gspann.itrack.common.exception;

/**
 * The exception class to be extended by all custom Exception classes
 * 
 * @author Y Kamesh Rao
 */
public class BaseRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -7163221818457123519L;

	// To be set by exception throwing class
	protected Object errorCode;

	public BaseRuntimeException(Object errorCode) {
		this.errorCode = errorCode;
	}

	public BaseRuntimeException(Object errorCode, Throwable cause) {
		super(cause);
	}

	public BaseRuntimeException(Object errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public BaseRuntimeException(Object errorCode, String message, Throwable cause) {
		super(message, cause);
	}

	public Object getErrorCode() {
		return errorCode;
	}
}
