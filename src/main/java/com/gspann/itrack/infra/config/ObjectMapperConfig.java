/**
 * 
 */
package com.gspann.itrack.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.jackson.datatype.money.MoneyModule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * @author suresh.babu
 *
 */
@Configuration
public class ObjectMapperConfig {
	
	@Bean
	public ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper()
			    .registerModule(new MoneyModule())
			    .registerModule(new JavaTimeModule());
		return mapper;
	}

}
