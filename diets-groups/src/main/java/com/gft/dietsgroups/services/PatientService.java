package com.gft.dietsgroups.services;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gft.dietsgroups.models.PatientModel;


@Service
@FeignClient(name = "patient-service")
public interface PatientService {
    List<PatientModel> findPatientByDietsGroupsId(@PathVariable UUID dietsGroupsId);
}
