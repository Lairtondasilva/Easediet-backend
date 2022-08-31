package com.gft.patient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.gft.patient.models.DietGroupModel;

@SpringBootApplication
@EnableEurekaClient
public class PatientApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientApplication.class, args);
	}

}
