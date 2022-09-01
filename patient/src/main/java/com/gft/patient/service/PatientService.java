package com.gft.patient.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gft.patient.models.PatientModel;
import com.gft.patient.repositories.PatientRepository;

@Service
public class PatientService {

    private PatientRepository patientRepository;

    public ResponseEntity<PatientModel> registerPatient(PatientModel patient) {
        if (checkIfPatientExists(patient.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already registered", null);
        }
        var patientSaved = patientRepository.save(patient);

        return ResponseEntity.status(HttpStatus.CREATED).body(patientSaved);
    }

    public boolean checkIfPatientExists(String email) {
        if (patientRepository.findByEmailContainingIgnoreCase(email).isPresent()) {
            return true;
        }
        return false;
    }

}
