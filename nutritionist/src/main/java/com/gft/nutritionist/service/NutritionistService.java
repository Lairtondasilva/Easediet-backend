package com.gft.nutritionist.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.nutritionist.model.NutritionistModel;
import com.gft.nutritionist.repository.NutritionistRepository;

@Service
public class NutritionistService {
    @Autowired
    private NutritionistRepository nutritionistRepository;

    public boolean checkIfNutritionistExist (String email){
        return true;
    }

}
