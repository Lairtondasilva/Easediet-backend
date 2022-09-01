package com.gft.patient.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.patient.models.PatientModel;
import com.gft.patient.repositories.PatientRepository;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/nutritionist/{nutritionistId}")
    public PatientModel findPatientByNutritionistId(@PathVariable String nutritionistId) {
        return new PatientModel("01e7dc6a-2962-11ed-81a0-047d7bb283ba", "Maria Carla", "maria@gmail.com", "12345678",
                Double.valueOf(1.69), Double.valueOf(63.5), 29, Double.valueOf(0.6), null,
                null, null, null);

    }
}
