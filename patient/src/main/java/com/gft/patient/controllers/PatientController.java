package com.gft.patient.controllers;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

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
import com.gft.patient.service.PatientService;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/all")
    public ResponseEntity<List<PatientModel>> getAll() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientModel> getById(@PathVariable UUID id) {
        // return patientService.getPatientById(id);
        return ResponseEntity.ok(new PatientModel(UUID.fromString("30e53eea-763d-4aa1-8fe1-738fa2d31c2e"), "Andrei",
                "Andrei@gmail.com", "dfaskjfasd", null, null, 24, null, null, "dfasdf", null, UUID.randomUUID()));
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
