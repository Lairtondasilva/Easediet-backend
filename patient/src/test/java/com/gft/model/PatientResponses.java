package com.gft.model;

import java.util.List;

import com.gft.patient.models.PatientModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PatientResponses {

    private List<PatientModel> patients;

}
