package com.cpt.payments.http;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cpt.payments.processing.CreateTxnRequest;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HttpServiceEngine {
	
	private RestTemplate restTemplate;

	private Gson gson;
	
	public HttpServiceEngine(RestTemplate restTemplate,
			Gson gson) {
		this.restTemplate = restTemplate;
		this.gson = gson;
	}
	
	public String makeHttpRequest() {
		try {
			
			String url = "http://localhost:8082/v1/payments";
			HttpMethod method = HttpMethod.POST;
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			CreateTxnRequest req = CreateTxnRequest.builder()
					.userId("user123")
		            .paymentMethod("APM")
		            .provider("TRUSTLY")
		            .paymentType("SALE")
		            .amount(150.75)
		            .currency("USD")
		            .txnStatus("CREATED")
		            .merchantTransactionReference("MERCH1235")
		            .build();
			
			
			
			log.info("Request to API: {}", req );
			
			String requestBody = gson.toJson(req);
			
			HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
			
			ResponseEntity<String> responseEntity = restTemplate.exchange(
					url,
					method,
					requestEntity,
					String.class);
			
			String responseBody = responseEntity.getBody();
			log.info("Response from API:{}", responseBody);
			
			return responseBody;
		} catch (Exception e) {
			log.error("Error while making API request:{}", e);
		}
		
		
		return "EXECUTED";
	}
	
	
}
