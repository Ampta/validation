package com.cpt.payments.http;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpServiceEngineTest {
	
	@Test
	public void testCreateTransaction() {
		log.info("Test case for create Transaction");
		
		HttpServiceEngine engine = new HttpServiceEngine(new RestTemplate(), new Gson());
		engine.makeHttpRequest();
	}
}
