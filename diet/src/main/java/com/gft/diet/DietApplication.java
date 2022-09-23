package com.gft.diet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@RefreshScope
@EnableFeignClients
public class DietApplication {

	public static void main(String[] args){
		SpringApplication.run(DietApplication.class, args);
	}

}
