package com.cpt.payments.constant;

public enum ErrorCodeEnum {
	GENERIC_ERROR("10000", "Unable to process. Please try again later."),
	INVALID_AMOUNT("10001", "Amount cannot be negative! Please correct the amount and try again.");
	
	private String errorCode;
	private String errorMessage;
	
	private ErrorCodeEnum(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	
	
}
