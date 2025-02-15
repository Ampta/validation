package com.cpt.payments.constant;

public enum ErrorCodeEnum {
	
	GENERIC_ERROR("10000", "Unable to process request, please try again later"),
	INVALID_AMOUNT("10001", "Amount cannot be negative, please correct the amount and try again"),
	MERCHANT_TXN_REF_EMPTY("10002", "Merchant transaction reference is null or empty."),
	DUPLICATE_MERCHANT_TXN_REF("10003", "Duplicate entry for merchant payment request"),
	PAYMENT_NOT_SAVED("10004", "Unable to save payment in DB, please try again later"),
	PAYMENT_ATTEMPT_THRESHOLD_EXCEEDED("10005", "Payment attemtps exceeded threshold, please try after some times"),
	SERVICE_UNAVAILABLE("10006", "Service unavilable, pleace try agian later"),
	HMAC_SIGNATURE_EMPTY("10007", "HMAC Signature is null or empty"),
	HMAC_SIGNATURE_INVALID("10008", "HAMC Signature is invalid");
	
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
