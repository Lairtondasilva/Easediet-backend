package com.gft.nutritionist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gft.nutritionist.model.NutritionistModel;
import com.gft.nutritionist.repository.NutritionistRepository;

@Service
public class NutritionistService {
    @Autowired
    private NutritionistRepository nutritionistRepository;

    public ResponseEntity<NutritionistModel> registerNutritionist(NutritionistModel nutritionist) {
        if (checkIfNutritionistExists(nutritionist.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already registered e-mail!", null);
        }

        var nutritionistSaved = nutritionistRepository.save(nutritionist);

        return ResponseEntity.status(HttpStatus.CREATED).body(nutritionistSaved);

    }

    public boolean checkIfNutritionistExists(String email) {
        if (nutritionistRepository.findByEmailContainingIgnoreCase(email).isPresent()) {
            return true;
        }
        return false;
    }

}
