package com.gft.diet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.diet.model.DietModel;

@RestController
@RequestMapping("/diets")
public class DietController {
    @GetMapping("/nutritionist/{nutritionistId}")
    public List<DietModel> getAllDietsOfANutritionistById(@PathVariable UUID nutritionistId) {

        var nutritionistDietList = new ArrayList<DietModel>();
        DietModel diet1 = new DietModel(UUID.randomUUID(), "Growth01", "Café,Leite", "null", "null", "aaa", "null",
                "null", "aaa",
                "null", "null", 0.4, null, "null", "null");

        nutritionistDietList.add(diet1);

        return nutritionistDietList;
    }

}
