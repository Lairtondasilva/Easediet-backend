package com.gft.nutritionist.controller;

import java.util.List;
import java.util.Optional;
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

import com.gft.nutritionist.model.NutritionistModel;
import com.gft.nutritionist.repository.NutritionistRepository;
import com.gft.nutritionist.services.DietService;
import com.gft.nutritionist.services.DietsGroupsService;
import com.gft.nutritionist.services.NutritionistService;


@RestController
@RequestMapping("/nutritionist")
@CrossOrigin(origins ="*",allowedHeaders = "*")
public class NutritionistController {

    @Autowired
    private NutritionistService nutritionistService;

    @Autowired
    private NutritionistRepository nutritionistRepository;

    @Autowired
    private DietsGroupsService dietsGroupsService;

    @Autowired
    private DietService dietService;

    @GetMapping("/all")
    public ResponseEntity <List<NutritionistModel>> getAllNutritionists(){
        return ResponseEntity.ok(nutritionistRepository.findAll());
    }

    @GetMapping("/{nutritionistId}")
    public ResponseEntity<NutritionistModel> getNutritionistById(@PathVariable UUID nutritionistId) {

        // var nutritionist = new NutritionistModel(UUID.randomUUID(), "Ingrid",
        //         "1234-5",
        //         "Ingrid@gmail.com", "12345678", "Healthy", null, null);

        // var groups = dietsGroupsService.findDietsGroupsByNutritionistId(nutritionistId);
        // nutritionist.setDietsGroups(groups);

        // var diets = dietService.findDietByNutritionistId(nutritionistId);
        // nutritionist.setDiets(diets);
        
        // return nutritionist;

        return nutritionistRepository.findById(nutritionistId).map(nutri -> ResponseEntity.ok(nutri)).orElse(ResponseEntity.notFound().build());

    }

    @PostMapping("/register")
    public ResponseEntity<Optional<NutritionistModel>> nutritionistRegisterPost(@RequestBody NutritionistModel nutritionist){
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(nutritionistService.registerNutritionist(nutritionist));
    }

    //@PostMapping("/login")

    @PutMapping("/update")
    public ResponseEntity<NutritionistModel> nutritionistUpdatePut(@Valid @RequestBody NutritionistModel nutritionist){
        return nutritionistService.updateNutritionist(nutritionist)
            .map(response -> ResponseEntity.ok(response))
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/id")
    public void nutritionistDelete (@PathVariable UUID nutritionistId){
        nutritionistRepository.deleteById(nutritionistId);
    }


}
