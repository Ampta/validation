package com.cpt.payments.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpt.payments.constant.Endpoints;
import com.cpt.payments.dto.PaymentRequestDTO;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.service.interfaces.PaymentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(Endpoints.V1_PAYMENTS)
@Slf4j
public class PaymentController {
	
	private PaymentService paymentService;
	private ModelMapper modelMapper;
	
	public PaymentController(PaymentService paymentService, 
				ModelMapper modelMapper) {
		this.paymentService = paymentService;
		this.modelMapper = modelMapper;
	}

	@PostMapping
	public String createPayment(@RequestBody PaymentRequest paymentRequest) {
		
		log.info("Payment request received: {}", paymentRequest);
		PaymentRequestDTO paymentRequestDTO = modelMapper.map(paymentRequest, PaymentRequestDTO.class);
		
		String response = paymentService.validateAndProcessPayment(paymentRequestDTO);
		
		return "Payment created succcesfully. | " + response;
	}

}
