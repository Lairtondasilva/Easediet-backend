package com.gft.nutritionist.services;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gft.nutritionist.model.PatientModel;

@FeignClient(name = "patient")
@Service
public interface PatientService {

    @GetMapping(value = "/patient/nutritionist/{nutritionistId}")
    PatientModel findByNutritionistId(@PathVariable("nutritionistId") String nutritionistId);

    @GetMapping(value = "/patient/email/{email}")
    Optional<PatientModel> findByPatientEmail(@PathVariable("email") String email);
}
