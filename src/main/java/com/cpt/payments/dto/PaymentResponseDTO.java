package com.cpt.payments.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentResponseDTO {
	
	private String tnxId;
	private String redirectUrl;
	
	public PaymentResponseDTO(String tnxId, String redirectUrl) {
		this.tnxId = tnxId;
		this.redirectUrl = redirectUrl;
	}
	
	

}
