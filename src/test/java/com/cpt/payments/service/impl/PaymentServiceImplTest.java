package com.cpt.payments.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PaymentServiceImplTest {
	
	@InjectMocks
	private PaymentServiceImpl paymentServiceImpl;
	
	@Test
	public void testMethod() {
		log.info("Test Code");
		
		paymentServiceImpl.validateAndProcessPayment(null);
		
		log.info("Test method End");
	}
	
	
}
