package com.gft.nutritionist.services;

import java.util.Optional;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.HttpHeaders;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gft.nutritionist.model.PatientModel;

import feign.Headers;
import feign.Param;

@FeignClient(name = "PATIENT-SERVICE")
@Service
public interface PatientService {

    @GetMapping(value = "/patient/nutritionist/{nutritionistId}")
    PatientModel findByNutritionistId(@PathVariable("nutritionistId") String nutritionistId);

    @GetMapping("/patient/email/{email}")
    Optional<PatientModel> findByPatientEmail(@PathVariable("email") String email);
}
