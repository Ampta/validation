package com.cpt.payments.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.session.DisableEncodeUrlFilter;

import com.cpt.payments.security.ExceptionHandlerFilter;
import com.cpt.payments.security.HmacFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	private ApplicationContext applicationContext;
	
	public SecurityConfiguration(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		http
	    .csrf(csrf -> csrf.disable())
	    .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
	    .addFilterBefore(new ExceptionHandlerFilter(), DisableEncodeUrlFilter.class)
	    .addFilterAfter(new HmacFilter(applicationContext), LogoutFilter.class)
	    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}
}

