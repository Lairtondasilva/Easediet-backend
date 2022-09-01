package com.gft.dietsgroups.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.dietsgroups.models.DietsGroupsModel;
import com.gft.dietsgroups.repositories.DietsGroupsRepository;
import com.gft.dietsgroups.services.PatientService;

@RestController
@RequestMapping("/diets-groups")
public class DietsGroupsController {

    @Autowired
    private DietsGroupsRepository dietsGroupsRepository;

    @Autowired(required = true)
    private PatientService patientService;

    @GetMapping("/{dietsGroupsId}")
    public DietsGroupsModel findById(@PathVariable String dietsGroupsId) {
        var group = new DietsGroupsModel("fdsajf", "dfsasdf", "dfasfa", "dfdas", Double.valueOf(100),
                Double.valueOf(200), "fdasfjdsa", "dasfjdlfads", null);
        group.setPatients(patientService.findPatientByDietsGroupsId(dietsGroupsId));
        return group;
    }

    @GetMapping("/nutritionist/{nutritionistId}")
    public List<DietsGroupsModel> getDietsGroupsByNutritionistId(@PathVariable String nutritionistId) {
        var group1 = new DietsGroupsModel("9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d",
                "diabetics", "", "sugar", Double.valueOf(0), Double.valueOf(20), "", "dfafdkas", null);
        var group2 = new DietsGroupsModel("9b1deb4d-3b7d-4bad-7bdd-2b0d7b3dcb6d",
                "diabetics", "", "sugar", Double.valueOf(0), Double.valueOf(20), "", "dfsaf", null);
        var groups = new ArrayList<DietsGroupsModel>();
        groups.add(group1);
        groups.add(group2);
        return groups;
    }
}
