package com.gft.nutritionist.services;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gft.nutritionist.model.DietModel;

@FeignClient(name = "diet")
@Service
public interface DietService {
    @GetMapping(value = "/diets/nutritionist/{nutritionistId}")
    List<DietModel> findDietByNutritionistId(@PathVariable String nutritionistId);
}
