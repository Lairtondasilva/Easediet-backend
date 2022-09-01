package com.gft.nutritionist.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.nutritionist.model.NutritionistModel;
import com.gft.nutritionist.repository.NutritionistRepository;
import com.gft.nutritionist.services.PatientService;

@RestController
@RequestMapping("/nutritionist")
public class NutritionistController {

    @Autowired
    private NutritionistRepository nutritionistRepository;
    @Autowired
    private PatientService patientService;

    @GetMapping
    public String all() {
        return "Hello World";
    }

    @GetMapping("/{nutritionistId}")
    public NutritionistModel getNutritionistById(@PathVariable String nutritionistId) {

        var nutritionist = new NutritionistModel("2953aa5c-2969-11ed-b392-047d7bb283ba", "Ingrid", "1234-5",
                "Ingrid@gmail.com", "12345678", "Healthy", null);
        var patient = patientService.findByNutritionistId(nutritionistId);
        nutritionist.setPatient(patient);

        return nutritionist;
    }
}
