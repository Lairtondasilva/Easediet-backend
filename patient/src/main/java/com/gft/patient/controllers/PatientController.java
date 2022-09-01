package com.gft.patient.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.patient.models.PatientModel;

@RestController
@RequestMapping("patient")
public class PatientController {

    @GetMapping("/diets-groups/{dietsGroupsId}")
    public List<PatientModel> getAllByGroupId(@PathVariable String dietsGroupsId) {
        var patient1 = new PatientModel("3241324dasffd", "Carlos", "Carlos@gmail.com", "123497454",
                Double.valueOf(1.75), Double.valueOf(63.5), 22, Double.valueOf(0.56), null, null, null, null, null);
        var group = new ArrayList<PatientModel>();
        group.add(patient1);
        return group;
    }
}
