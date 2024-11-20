package com.cpt.payments.service.impl.validators;

import org.springframework.stereotype.Service;

import com.cpt.payments.dto.PaymentRequestDTO;
import com.cpt.payments.service.interfaces.Validator;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class ValidatorRule1 implements Validator {

	@Override
	public void validate(PaymentRequestDTO paymentRequestDTO) {
		log.info("ValidatorRule1 | Validating payment request: {}", paymentRequestDTO);
		
		log.info("ValidatorRule1 | payment request validated successfully");

	}

}
