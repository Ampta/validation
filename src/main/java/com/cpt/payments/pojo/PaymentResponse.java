package com.cpt.payments.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor 
public class PaymentResponse {
	
	private String tnxId;
	private String redirectUrl;
	
	public PaymentResponse(String tnxId, String redirectUrl) {
		this.tnxId = tnxId;
		this.redirectUrl = redirectUrl;
	}
	
	

}
