package com.gft.payment.services;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gft.payment.models.NutritionistModel;

@FeignClient("nutritionist")
@Service
public interface NutritionistService {

    @GetMapping("/nutritionist/{id}")
    public NutritionistModel getNutritionistById(@PathVariable UUID id);
}
