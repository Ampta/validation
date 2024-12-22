package com.cpt.payments.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpt.payments.constant.Endpoints;
import com.cpt.payments.constant.ErrorCodeEnum;
import com.cpt.payments.dto.PaymentRequestDTO;
import com.cpt.payments.dto.PaymentResponseDTO;
import com.cpt.payments.exception.ValidationException;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.pojo.PaymentResponse;
import com.cpt.payments.service.impl.HmacSha256ServiceImpl;
import com.cpt.payments.service.interfaces.PaymentService;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(Endpoints.V1_PAYMENTS)
@Slf4j
public class PaymentController {

	private PaymentService paymentService;
	private ModelMapper modelMapper;
	private HmacSha256ServiceImpl hmacSha256Service; 
	private Gson gson;

	public PaymentController(PaymentService paymentService, 
			ModelMapper modelMapper,
			HmacSha256ServiceImpl hmacSha256Service,
			Gson gson) {
		this.paymentService = paymentService;
		this.modelMapper = modelMapper;
		this.hmacSha256Service = hmacSha256Service; 
		this.gson = gson;
	}

	@PostMapping
	public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest paymentRequest) {

		log.info("Payment request received: {}", paymentRequest);
		
		PaymentRequestDTO paymentRequestDTO = modelMapper.map(paymentRequest, PaymentRequestDTO.class);

		PaymentResponseDTO response = paymentService.validateAndProcessPayment(paymentRequestDTO);

		PaymentResponse paymentRes = modelMapper.map(response, PaymentResponse.class);

		log.info("Returning from controller paymentRes {}", paymentRes);

		return new ResponseEntity<>(paymentRes, HttpStatus.CREATED);
	}

}
