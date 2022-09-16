package com.gft.diet.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.client.RestTemplate;

import com.gft.diet.model.DietModel;
import com.gft.diet.model.FoodList;
import com.gft.diet.repository.DietRepository;
import com.gft.diet.service.DietService;
import com.gft.diet.service.FoodService;
import com.gft.diet.translation.RespText;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RequestMapping("/diets")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@CircuitBreaker(name = "default")
@Retry(name = "default")
public class DietController {

    @Autowired
    private DietRepository dietRepository;

    @Autowired
    private DietService dietService;

    @Autowired
    private FoodService foodService;

    @PostMapping("/foods")
    @Retry(name = "default", fallbackMethod = "GetFoodListFail")
    public FoodList getFoodList(@RequestBody DietModel diet) throws IOException, InterruptedException {
        List<String> foods = new ArrayList<>();

        var response = new RestTemplate().postForEntity("http://localhost:9000/translate/pt/en",
                diet, RespText.class);

        var foodList = response.getBody().getTranslatedText().split(",");

        for (String food : foodList) {
            foods.add(food);
        }
        return foodService.getFood(foods);
    }

    public ResponseEntity<FoodList> GetFoodListFail(Exception e) {
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DietModel>> getAllDiets() {
        return ResponseEntity.ok(dietRepository.findAll());
    }

    @GetMapping("/{dietId}")
    public ResponseEntity<DietModel> getDietById(@PathVariable UUID dietId) {
        return dietRepository.findById(dietId).map(diet -> ResponseEntity.ok(diet))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/nutritionist/{nutritionistId}")
    @Retry(name = "default", fallbackMethod = "getAllDietsOfNutritionistId")
    public ResponseEntity<List<DietModel>> getAllDietsOfNutritionistById(@PathVariable UUID nutritionistId) {
        return ResponseEntity.ok(dietRepository.findAllByNutritionistId(nutritionistId));
    }

    public ResponseEntity<List<DietModel>> getAllDietsOfNutritionistId(Exception e) {
        return ResponseEntity.badRequest().body(null);
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
