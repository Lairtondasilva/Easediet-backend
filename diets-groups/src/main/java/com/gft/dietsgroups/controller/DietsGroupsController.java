package com.gft.dietsgroups.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.gft.dietsgroups.models.DietGroupModel;
import com.gft.dietsgroups.repositories.DietGroupRepository;
import com.gft.dietsgroups.services.PatientService;

@RestController
@RequestMapping("/diets-groups")
public class DietsGroupsController {

    @Autowired
    private DietGroupRepository dietGroupRepository;

    @Autowired(required = true)
    private PatientService patientService;

    @GetMapping("/all")
    public ResponseEntity<List<DietGroupModel>> all() {
        return ResponseEntity.ok(dietGroupRepository.findAll());
    }

    @GetMapping("/{dietGroupId}")
    public ResponseEntity<DietGroupModel> findById(@PathVariable UUID dietGroupId) {
        return dietGroupRepository.findById(dietGroupId).map(group -> {
            var patients = patientService.findPatientByDietsGroupsId(group.getId());
            group.setPatients(patients);
            return ResponseEntity.ok(group);
        }).orElse(ResponseEntity.notFound().build());
    }

    // @Retry(name = "default", fallbackMethod =
    // "getDietsGroupsByNutritionistIdFail")
    @GetMapping(value = "/nutritionist/{nutritionistId}")
    public List<DietGroupModel> findDietsGroupsByNutritionistId(@PathVariable UUID nutritionistId) {
        return dietGroupRepository.findAllByNutritionistId(nutritionistId);
    }

    public List<DietGroupModel> getDietsGroupsByNutritionistIdFail() {
        return List.of(null);
    }

    @PostMapping
    public ResponseEntity<DietGroupModel> createDietGroup(@RequestBody DietGroupModel dietGroup) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dietGroupRepository.save(dietGroup));
    }

    @PutMapping
    public ResponseEntity<DietGroupModel> updateDietGroup(@RequestBody DietGroupModel dietGroup) {
        if (dietGroupRepository.findById(dietGroup.getId()).isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.CREATED).body(dietGroupRepository.save(dietGroup));
    }

    @DeleteMapping("/dietGroupId")
    public void deleteDietGroup(@PathVariable UUID dietGroupId) {
        dietGroupRepository.deleteById(dietGroupId);
    }

}
