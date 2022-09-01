package com.gft.nutritionist.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DietsGroupsModel {

    @EqualsAndHashCode.Include
    private String id;

    private String name;

    private String description;

    private String ctrlParamName;

    private double ctrlParamMinVal;

    private double ctrlParamMaxVal;

    private String nutritionistId;

    private String dietId;

    private List<PatientModel> patients;
}
