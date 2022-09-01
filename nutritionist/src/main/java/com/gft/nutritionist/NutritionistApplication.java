package com.gft.nutritionist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class NutritionistApplication {

	public static void main(String[] args) {
		SpringApplication.run(NutritionistApplication.class, args);
	}

}
