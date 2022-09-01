package com.gft.nutritionist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.nutritionist.model.NutritionistModel;
import com.gft.nutritionist.repository.NutritionistRepository;
import com.gft.nutritionist.services.DietsGroupsService;
import com.gft.nutritionist.services.PatientService;

@RestController
@RequestMapping("/nutritionist")
public class NutritionistController {

    @Autowired
    private NutritionistRepository nutritionistRepository;
    @Autowired
    private PatientService patientService;
    @Autowired
    private DietsGroupsService dietsGroupsService;

    @GetMapping
    public String all() {
        return "Hello World";
    }

    @GetMapping("/{nutritionistId}")
    public NutritionistModel getNutritionistById(@PathVariable String nutritionistId) {

        var nutritionist = new NutritionistModel("2953aa5c-2969-11ed-b392-047d7bb283ba", "Ingrid", "1234-5",
                "Ingrid@gmail.com", "12345678", "Healthy", null);
        var groups = dietsGroupsService.findByDietsGroupsByNutritionistId(nutritionistId);
        nutritionist.setDietsGroups(groups);
        return nutritionist;
    }
}
