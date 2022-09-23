package com.gft.nutritionist.services;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gft.nutritionist.model.DietGroupModel;

@Service
@FeignClient(name = "diets-groups")
public interface DietGroupService {

    @GetMapping(value = "/diets-groups/nutritionist/{nutritionistId}")
    List<DietGroupModel> findDietsGroupsByNutritionistId(@PathVariable UUID nutritionistId);
}
