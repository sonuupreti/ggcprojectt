package com.gspann.itrack.adapter.persistence.exception;

public class IdGenerationException extends RuntimeException {

	private static final long serialVersionUID = -3853277589620431332L;

	// To be set by exception throwing class
	protected String errorCode;

	public IdGenerationException(String errorCode) {
		this.errorCode = errorCode;
	}

	public IdGenerationException(String errorCode, Throwable cause) {
		super(cause);
	}

	public IdGenerationException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public IdGenerationException(String errorCode, String message, Throwable cause) {
		super(message, cause);
	}

	public String getErrorCode() {
		return errorCode;
	}
}
