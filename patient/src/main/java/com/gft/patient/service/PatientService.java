package com.gft.patient.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gft.patient.models.PatientModel;
import com.gft.patient.models.UserLogin;
import com.gft.patient.repositories.PatientRepository;
import com.gft.patient.util.JwtUtil;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // registers a patient
    public ResponseEntity<PatientModel> registerPatient(PatientModel patient) {
        if (checkIfPatientExists(patient.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already registered", null);
        }
        patient.setPassword(criptografarSenha(patient.getPassword()));

        var patientSaved = patientRepository.save(patient);

        return ResponseEntity.status(HttpStatus.CREATED).body(patientSaved);
    }

    // updates a patient
    public ResponseEntity<PatientModel> updatePatient(PatientModel patient) {
        if (patientRepository.findById(patient.getId()).isPresent()) {
            var databasePatient = patientRepository.findByEmailContainingIgnoreCase(patient.getEmail());
            if (databasePatient.isEmpty() && databasePatient.get().getId() != patient.getId()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This email is already in use", null);
            }
            return ResponseEntity.ok(patientRepository.save(patient));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found in database", null);
    }

    // returns all patients
    public ResponseEntity<List<PatientModel>> getAllPatients() {
        return ResponseEntity.ok(patientRepository.findAll());
    }

    // returns patient by id
    public ResponseEntity<PatientModel> getPatientById(UUID id) {
        Optional<PatientModel> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            return ResponseEntity.ok(patient.get());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found in database", null);
    }

    // deletes a patient
    public void delete(UUID id) {
        patientRepository.deleteById(id);
    }

    public ResponseEntity<List<PatientModel>> getAllPatientsByGroupId(UUID id) {
        return ResponseEntity.ok(patientRepository.findAllByGroupId(id));
    }

    public boolean checkIfPatientExists(String email) {
        if (patientRepository.findByEmailContainingIgnoreCase(email).isPresent()) {
            return true;
        }
        return false;
    }

    public Optional<String> patientAuthentication(UserLogin userLogin) {

        Optional<PatientModel> patient = patientRepository
                .findByEmailContainingIgnoreCase(userLogin.getUsername());

        if (patient.isPresent()) {
            if (compararSenhas(userLogin.getPassword(), patient.get().getPassword())) {
                userLogin.setPassword(patient.get().getPassword());
                userLogin.setId(patient.get().getId());
                userLogin.setRole(patient.get().getRole());

                return Optional.of(jwtUtil.generateToken(userLogin));
            }
        }

        return Optional.empty();

    }

    private boolean compararSenhas(String senhaDigitada, String senhaBD) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.matches(senhaDigitada, senhaBD);

    }

    private String criptografarSenha(String senha) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.encode(senha);

    }

}
