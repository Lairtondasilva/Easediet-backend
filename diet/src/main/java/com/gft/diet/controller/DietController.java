package com.gft.diet.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.diet.model.DietModel;
import com.gft.diet.repository.DietRepository;
import com.gft.diet.service.DietService;

@RestController
@RequestMapping("/diets")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DietController {

    @Autowired
    private DietRepository dietRepository;

    @Autowired
    private DietService dietService;

    @GetMapping("/{dietId}")
    public ResponseEntity<DietModel> getDietById(@PathVariable UUID dietId) {
        return dietRepository.findById(dietId).map(diet -> ResponseEntity.ok(diet))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/nutritionist/{nutritionistId}")
    public ResponseEntity<List<DietModel>> getAllDietsOfANutritionistById(@PathVariable UUID nutritionistId) {
        return ResponseEntity.ok(dietRepository.findAllByNutritionistId(nutritionistId));
    }

    @GetMapping("/dietgroup/{dietGroupId}")
    public ResponseEntity<DietModel> getDietOfDietGroupById(@PathVariable UUID dietGroupId) {
        return dietRepository.findByDietGroupId(dietGroupId).map(diet -> ResponseEntity.ok(diet))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DietModel> dietRegister(@Valid @RequestBody DietModel diet) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dietRepository.save(diet));
    }

    @PutMapping
    public ResponseEntity<DietModel> dietUpdate(@Valid @RequestBody DietModel diet) {
        return dietService.updateDiet(diet).map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{dietId}")
    public void dietDelete(@PathVariable UUID dietId) {
        dietRepository.deleteById(dietId);
    }

}
