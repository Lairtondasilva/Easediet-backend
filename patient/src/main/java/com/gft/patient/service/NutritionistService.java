package com.gft.patient.service;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gft.patient.models.NutritionistModel;

@FeignClient(name = "nutritionist-service")
@Service
public interface NutritionistService {

    @GetMapping("/nutritionist/email/{email}")
    Optional<NutritionistModel> findNutritionistByEmail(@PathVariable String email);
}
