package com.cpt.payments.service.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.cpt.payments.cache.ValidationRulesCache;
import com.cpt.payments.constant.ValidatorEnum;
import com.cpt.payments.dao.ValidationRuleDao;
import com.cpt.payments.dto.PaymentRequestDTO;
import com.cpt.payments.dto.PaymentResponseDTO;
import com.cpt.payments.service.interfaces.PaymentService;
import com.cpt.payments.service.interfaces.Validator;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
	
	private List<String> activeValidatorRules;
	
	private ApplicationContext applicationContext;
	
	private ValidationRulesCache validationRulesCache;

	public PaymentServiceImpl(ApplicationContext applicationContext,
			ValidationRulesCache validationRulesCache) {
		this.applicationContext = applicationContext;
		this.validationRulesCache = validationRulesCache;
	}

	@Override
	public PaymentResponseDTO validateAndProcessPayment(PaymentRequestDTO paymentRequest) {
		
		log.info("Payment request received: {}", paymentRequest);
		
		validationRulesCache.getValidationRulesList().forEach(
				rule -> triggerValidationRule(paymentRequest, rule));
		
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
	
	@PostConstruct
	public void loadValidatorRules() {
		validationRulesCache.loadValidatorRulesAndParams();
		log.info("Loaded validator rules from cache");
		
	}

}
