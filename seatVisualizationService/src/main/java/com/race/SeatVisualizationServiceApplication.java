package com.race;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableEurekaClient
public class SeatVisualizationServiceApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(SeatVisualizationServiceApplication.class);

	public static void main(String[] args) {
		LOGGER.info(" Inside SeatVisualizationServiceApplication Starter Class");
		SpringApplication.run(SeatVisualizationServiceApplication.class, args);
	}

}
