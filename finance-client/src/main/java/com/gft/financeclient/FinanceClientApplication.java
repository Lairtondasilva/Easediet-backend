package com.gft.financeclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FinanceClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceClientApplication.class, args);
	}

}