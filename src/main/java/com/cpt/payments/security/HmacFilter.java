package com.cpt.payments.security;


import java.io.BufferedReader;
import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cpt.payments.constant.ErrorCodeEnum;
import com.cpt.payments.exception.ValidationException;
import com.cpt.payments.service.interfaces.HmacSha256Service;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HmacFilter extends OncePerRequestFilter{

	private ApplicationContext applicationContext;
	private HmacSha256Service hmacSha256Service;
	private Gson gson;

	public HmacFilter(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.hmacSha256Service = applicationContext.getBean(HmacSha256Service.class);
		this.gson = applicationContext.getBean(Gson.class);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info("Starting HmacFilter execution | applicationContext: {} | hmacSha256Service: {} ", 
				applicationContext, hmacSha256Service);

		String receivedHmacSignature = request.getHeader("hmac-signature");
		log.info("Received HMAC Signature: {}", receivedHmacSignature);

		if(receivedHmacSignature == null || receivedHmacSignature.trim().isEmpty()) {
			log.error("received HMAC is null or empty");

			throw new ValidationException(
					ErrorCodeEnum.HMAC_SIGNATURE_EMPTY.getErrorCode(), 
					ErrorCodeEnum.HMAC_SIGNATURE_EMPTY.getErrorMessage(), 
					HttpStatus.BAD_REQUEST);
		}

		WrappedRequest wrappedRequest = new WrappedRequest(request);
		String requestData = getNormalizedJson(wrappedRequest.getBody());
		log.info("Request body: {}", requestData);

		boolean isHmacVerified = hmacSha256Service.verifyHMAC(requestData, receivedHmacSignature);

		log.info("isHmacVerified: {}", isHmacVerified);

		if (isHmacVerified) {
			log.info("HMAC Signature is verified");

			SecurityContext context = SecurityContextHolder.createEmptyContext(); 
			Authentication authentication = new HmacAuthenticationToken("Ecom", "");
			context.setAuthentication(authentication);
			SecurityContextHolder.setContext(context);

			filterChain.doFilter(wrappedRequest, response);
			return;
		}

		log.error("HMAC Signature verification failed");
		throw new ValidationException(
				ErrorCodeEnum.HMAC_SIGNATURE_INVALID.getErrorCode(),
				ErrorCodeEnum.HMAC_SIGNATURE_INVALID.getErrorMessage(),
				HttpStatus.BAD_REQUEST);

	}


	public String getNormalizedJson(String rawJson) {
		// Parse the raw JSON string
		JsonElement jsonElement = JsonParser.parseString(rawJson);
		// Convert it back to a JSON string
		return gson.toJson(jsonElement);
	}

}
