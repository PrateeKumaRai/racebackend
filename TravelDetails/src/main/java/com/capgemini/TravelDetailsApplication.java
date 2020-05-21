package com.capgemini;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableEurekaClient
public class TravelDetailsApplication {
	public static void main(String[] args) {
		SpringApplication.run(TravelDetailsApplication.class, args);
	}

}



