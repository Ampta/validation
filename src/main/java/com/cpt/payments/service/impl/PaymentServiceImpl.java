package com.cpt.payments.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.cpt.payments.constant.ValidatorEnum;
import com.cpt.payments.dto.PaymentRequestDTO;
import com.cpt.payments.dto.PaymentResponseDTO;
import com.cpt.payments.service.interfaces.PaymentService;
import com.cpt.payments.service.interfaces.Validator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
	
	@Value("${validator.rules}")
	private String validatorRules;
	
	private ApplicationContext applicationContext;

	public PaymentServiceImpl(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public PaymentResponseDTO validateAndProcessPayment(PaymentRequestDTO paymentRequest) {
		
		log.info("Payment request received: {}", paymentRequest);
		String[] rules = validatorRules.split(",");
		for(String rule : rules) {
			triggerValidationRule(paymentRequest, rule);
		}
		
		log.info("Payment request processed successfully. All rules passed");
		
		String txnId = "txn1234";
		String redirectUrl = "www.google.com";
		
		PaymentResponseDTO paymentResponse = new PaymentResponseDTO(txnId, redirectUrl);
		
		log.info("Payment Response: {}", paymentResponse);
		
		return paymentResponse;
	}

	private String triggerValidationRule(PaymentRequestDTO paymentRequest, String rule) {
		rule = rule.trim();
		log.info("Validating payment request using rule {}", rule);
		
		Validator validator = null;
		Class<? extends Validator> validatorClass = ValidatorEnum.getClassByName(rule);;
		
		if(validatorClass != null) {
			validator = applicationContext.getBean(validatorClass);
			if(validator != null) {
				log.info("Calling validator rule: {}", rule);
				validator.validate(paymentRequest);
			}
		}
		
		if(validatorClass == null || validator == null) {
			log.error("Either Validator class not found or Validator instance not found for this rule " + 
							"|rule: {} | alidatorClass: {} | validator: {}", rule, validatorClass, validator);
		}
		return rule;
	}

}
