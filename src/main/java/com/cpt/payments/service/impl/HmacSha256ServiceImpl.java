package com.cpt.payments.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

@Service
public class HmacSha256ServiceImpl {

	public String generateHmac() {
		String secretKey = "THIS_IS_MY_SECRET";
		String jsonInput = "{\"data\": \"your_json_data_here\"}";

		String signature = null;
		try {
			// Create a SecretKeySpec object from the secret key
			SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");

			// Initialize the HMAC-SHA256 Mac instance
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(keySpec);

			// Compute the HMAC-SHA256 signature
			byte[] signatureBytes = mac.doFinal(jsonInput.getBytes(StandardCharsets.UTF_8));

			// Encode the signature in Base64
			signature = Base64.getEncoder().encodeToString(signatureBytes);

			System.out.println("HMAC-SHA256 Signature: " + signature);
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			e.printStackTrace();
		}

		return signature;

	}

//	public static void main(String[] args) {
//		HmacSha256ServiceImpl hmacSha256ServiceImpl = new HmacSha256ServiceImpl();
//		String hmac = hmacSha256ServiceImpl.generateHmac();
//
//		System.out.println("HMAC: " + hmac);
//	}
}

