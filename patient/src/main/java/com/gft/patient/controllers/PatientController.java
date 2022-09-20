package com.gft.patient.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.patient.models.PatientModel;
import com.gft.patient.models.UserLogin;
import com.gft.patient.service.PatientService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/patient")
@CircuitBreaker(name = "default")
@Retry(name = "default")
public class PatientController {

    @Autowired
    private PatientService patientService;

    private Logger logger = LoggerFactory.getLogger(PatientController.class);

    // @PostMapping("/login")
    // public ResponseEntity<Optional<String>> login(@RequestBody UserLogin
    // userLogin) {
    // return
    // ResponseEntity.ok().body(patientService.patientAuthentication(userLogin));
    // }

    @GetMapping("/all")
    public ResponseEntity<List<PatientModel>> getAll() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientModel> getById(@PathVariable UUID id) {
        return patientService.getPatientById(id);
    }

    @GetMapping("/diet-groups/{dietsGroupsId}")
    @Retry(name = "default", fallbackMethod = "getAllByGroupIdFail")
    public ResponseEntity<List<PatientModel>> getAllByGroupId(@PathVariable UUID dietsGroupsId) {
        // var resp = new RestTemplate().getForEntity("http://localhost:8080/food",
        // String.class);
        return patientService.getAllPatientsByGroupId(dietsGroupsId);
    }

    public ResponseEntity<String> getAllByGroupIdFail(Exception e) {
        return ResponseEntity.badRequest().body("The service is currently unavailable.");
    }

    @PostMapping("/register")
    public ResponseEntity<PatientModel> registerPatient(@RequestBody @Valid PatientModel patient) {
        return patientService.registerPatient(patient);
    }

    @PutMapping
    public ResponseEntity<PatientModel> updatePatient(@RequestBody @Valid PatientModel patient) {
        return patientService.updatePatient(patient);
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable UUID id) {
        patientService.delete(id);
    }
}
