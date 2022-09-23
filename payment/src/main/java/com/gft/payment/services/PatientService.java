package com.gft.payment.services;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gft.payment.models.PatientModel;

@RequestMapping("/patient")
@FeignClient("patient")
public interface PatientService {

    @GetMapping("/{id}")
    public PatientModel getPatientById(@PathVariable UUID id);

    @GetMapping("/all")
    public List<PatientModel> getAllPatients();
}
