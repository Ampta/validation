package com.cpt.payments.dto;

import lombok.Data;

@Data
public class PaymentResponseDTO {
	
	private String tnxId;
	private String redirectUrl;
	
	public PaymentResponseDTO(String tnxId, String redirectUrl) {
		this.tnxId = tnxId;
		this.redirectUrl = redirectUrl;
	}
	
	

}
