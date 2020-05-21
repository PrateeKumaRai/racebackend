package com.capgemini.race.asset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
@EnableEurekaClient
public class UserAssetServiceApplication {
	private static final Logger logger=LoggerFactory.getLogger(UserAssetServiceApplication.class);
	public static void main(String[] args) {
		logger.debug("Starting application");
		SpringApplication.run(UserAssetServiceApplication.class, args);
		System.out.println(" **************  Finally application is up");
		logger.debug("Exiting application");
	}
}
