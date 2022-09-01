package com.gft.dietsgroups;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DietsGroupsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DietsGroupsApplication.class, args);
	}

}
