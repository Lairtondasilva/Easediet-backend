package com.gft.patient.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gft.patient.repositories.PatientRepository;

@Service
public class Patient {

    @Autowired
    private PatientRepository patientRepository;

}
