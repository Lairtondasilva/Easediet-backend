package com.gft.diet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gft.diet.model.DietModel;
import com.gft.diet.repository.DietRepository;

@Service
public class DietService {
    
    @Autowired
    private DietRepository dietRepository;

    public boolean checkIfDietAlreadyExists (String dietName){
        if (dietRepository.findByNameContainingIgnoreCase(dietName).isPresent()){
            return true;
        }
        return false;
    }

    public ResponseEntity<DietModel> dietRegister(DietModel diet){
        if (checkIfDietAlreadyExists(diet.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Already registered diet",null);
        }
        var dietSaved = dietRepository.save(diet);
        return ResponseEntity.status(HttpStatus.CREATED).body(dietSaved);
    }

    public Optional<DietModel> updateDiet (DietModel diet){
        if (dietRepository.findById(diet.getId()).isPresent()){
            List <DietModel> sameNameDiets = dietRepository.findAllByNameContainingIgnoreCase(diet.getName());

            for(DietModel d:sameNameDiets){
                if(d.getNutritionistId().equals(diet.getNutritionistId()) && d.getId()!=diet.getId()){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Diet name already used for this user at the database!");
                }
                return Optional.of(dietRepository.save(diet));
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Diet not found at the database!");
    }
}
