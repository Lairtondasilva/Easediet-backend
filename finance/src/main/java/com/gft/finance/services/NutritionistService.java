package com.gft.finance.services;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.gft.finance.models.NutritionistModel;

@FeignClient("nutritionist-service")
public interface NutritionistService {

    @GetMapping("/nutritionist/{id}")
    public NutritionistModel getNutritionistById(@PathVariable UUID id);
}
