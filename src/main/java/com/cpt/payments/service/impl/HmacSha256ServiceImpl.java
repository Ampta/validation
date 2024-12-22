package com.cpt.payments.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cpt.payments.service.interfaces.HmacSha256Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HmacSha256ServiceImpl implements HmacSha256Service{

	private static final String HMAC_SHA256 = "HmacSHA256";

	@Value("${ecom.hmac.secret}")
	private String secretKey;

	@Override
	public String calculateHMAC(String jsonData) {
		try {
		    // Create a SecretKeySpec object from the secret key
		    SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), HMAC_SHA256);

		    // Initialize the HMAC-SHA256 Mac instance
		    Mac mac = Mac.getInstance(HMAC_SHA256);
		    mac.init(keySpec);

		    // Compute the HMAC
		    byte[] hmac = mac.doFinal(jsonData.getBytes(StandardCharsets.UTF_8));

		    // Convert the HMAC bytes to a signature
		    String signature = Base64.getEncoder().encodeToString(hmac);
		    
		    System.out.println("HMAC-SHA256 Signature: " + signature);
		    
		    return signature;
		  } catch (NoSuchAlgorithmException | InvalidKeyException e) {
		    e.printStackTrace();
		  }catch (Exception e) {
			e.printStackTrace();
		}
		
		log.error("HMAC-SHA256 failed to generate signature null");
		return null;
	}

	@Override
	public boolean verifyHMAC(String data, String receivedHmac) {
		String generatedSignature = calculateHMAC(data);

		log.info("Data: {}", data);
	    log.info("receivedHmac: " + receivedHmac);
	    log.info("generatedSignature: " + generatedSignature);

	    if (generatedSignature != null && generatedSignature.equals(receivedHmac)) {
	        log.info("HMAC-SHA256 Signature is valid");
	        return true;
	    }
	    System.out.println("HMAC-SHA256 Signature is invalid");
	    return false;
	}


}

