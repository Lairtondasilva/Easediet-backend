package com.gft.payment.services;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gft.payment.models.PatientModel;

@Service
@FeignClient("patient")
public interface PatientService {

    @GetMapping("/patient/{id}")
    public PatientModel getPatientById(@PathVariable UUID id);

    @GetMapping("/patient/all")
    public List<PatientModel> getAllPatients();
}
