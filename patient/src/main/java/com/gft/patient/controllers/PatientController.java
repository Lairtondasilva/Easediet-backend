package com.gft.patient.controllers;

import java.util.List;
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
import org.springframework.web.client.RestTemplate;

import com.gft.patient.models.PatientModel;
import com.gft.patient.service.PatientService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    private Logger logger = LoggerFactory.getLogger(PatientController.class);

    @GetMapping("/test")
     //@Retry(name = "test", fallbackMethod = "fallback")
    // @CircuitBreaker(name = "default", fallbackMethod = "fallback")
   @RateLimiter(name = "default")
    public String test() {
        logger.info("request to test is received");
         //var response = new RestTemplate().getForEntity("http://localhost:8080/food",
        //.class);
        // return response.getBody();
        return "Hello World";
    }

    public String fallback(Exception ex) {
        return "Hello guys!";
    }

    @GetMapping("/all")
    public ResponseEntity<List<PatientModel>> getAll() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientModel> getById(@PathVariable UUID id) {
        return patientService.getPatientById(id);
    }

    @GetMapping("/diets-groups/{dietsGroupsId}")
    public ResponseEntity<List<PatientModel>> getAllByGroupId(@PathVariable UUID dietsGroupsId) {
        return patientService.getAllPatientsByGroupId(dietsGroupsId);
    }

    @PostMapping
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
