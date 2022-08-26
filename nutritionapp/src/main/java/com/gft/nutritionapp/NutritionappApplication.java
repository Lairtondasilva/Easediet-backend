package com.gft.nutritionapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class NutritionappApplication {

	public static void main(String[] args) {
		SpringApplication.run(NutritionappApplication.class, args);
	}

}
