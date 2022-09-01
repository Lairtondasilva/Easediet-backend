package com.gft.nutritionist.services;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gft.nutritionist.model.DietsGroupsModel;

@FeignClient(name = "diets-groups-service")
public interface DietsGroupsService {

    @GetMapping(value = "/diets-groups/nutritionist/{nutritionistId}")
    List<DietsGroupsModel> findByDietsGroupsByNutritionistId(@PathVariable("nutritionistId") String nutritionistId);
}
