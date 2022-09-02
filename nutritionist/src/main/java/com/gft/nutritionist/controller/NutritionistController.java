package com.gft.nutritionist.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.nutritionist.model.NutritionistModel;
import com.gft.nutritionist.repository.NutritionistRepository;
import com.gft.nutritionist.services.DietService;
import com.gft.nutritionist.services.DietsGroupsService;

@RestController
@RequestMapping("/nutritionist")
public class NutritionistController {

    @Autowired
    private NutritionistRepository nutritionistRepository;

    @Autowired
    private DietsGroupsService dietsGroupsService;

    @Autowired
    private DietService dietService;

    @GetMapping
    public String all() {
        return "Hello World";
    }

    @GetMapping("/{nutritionistId}")
    public NutritionistModel getNutritionistById(@PathVariable String nutritionistId) {

        var nutritionist = new NutritionistModel(UUID.randomUUID(), "Ingrid",
                "1234-5",
                "Ingrid@gmail.com", "12345678", "Healthy", null, null);

        var groups = dietsGroupsService.findDietsGroupsByNutritionistId(nutritionistId);
        nutritionist.setDietsGroups(groups);

        var diets = dietService.findDietByNutritionistId(nutritionistId);
        nutritionist.setDiets(diets);

        return nutritionist;

    }
}
