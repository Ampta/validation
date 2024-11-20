package com.cpt.payments.service.interfaces;

import com.cpt.payments.dto.PaymentRequestDTO;

public interface PaymentService {
	public String validateAndProcessPayment(PaymentRequestDTO paymentRequest);
}
