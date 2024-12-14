package com.cpt.payments.http;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerErrorException;

import com.cpt.payments.constant.ErrorCodeEnum;
import com.cpt.payments.exception.ValidationException;
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

	public ResponseEntity<String> makeHttpRequest(HttpRequest httpRequest) {
		try {
			// Creating Request entity
			HttpEntity<String> requestEntity = new HttpEntity<>(
					httpRequest.getRequestBody(), httpRequest.getHeaders());

			ResponseEntity<String> responseEntity = restTemplate.exchange(
					httpRequest.getUrl(),
					httpRequest.getMethod(),
					requestEntity,
					String.class);

			String responseBody = responseEntity.getBody();
			log.info("Response from API:{}", responseBody);

			return responseEntity;
		}
		catch(HttpClientErrorException | HttpServerErrorException e) {
			log.error("Error while making API request:{}", e);

			return ResponseEntity.status((e).getStatusCode()).body((e).getResponseBodyAsString());
		}
		catch(Exception e) {
			log.error("Error while making API request:{}", e);

//			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("");
			
			throw new ValidationException(
					ErrorCodeEnum.SERVICE_UNAVAILABLE.getErrorCode(), 
					ErrorCodeEnum.SERVICE_UNAVAILABLE.getErrorMessage(), 
					HttpStatus.SERVICE_UNAVAILABLE);
		}

	}


}
