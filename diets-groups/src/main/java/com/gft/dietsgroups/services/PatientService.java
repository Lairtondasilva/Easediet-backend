package com.gft.dietsgroups.services;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gft.dietsgroups.models.PatientModel;

@FeignClient("patient-service")
@Service
public interface PatientService {

    @GetMapping(value = "/patient/diets-groups/{dietsGroupsId}")
    List<PatientModel> findPatientByDietsGroupsId(@PathVariable String dietsGroupsId);
}
