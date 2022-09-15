package com.gft.payment.models;

import java.util.List;
import java.util.UUID;

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
    private UUID id;

    private String name;

    private String description;

    private String ctrlParamName;

    private double ctrlParamMinVal;

    private double ctrlParamMaxVal;

    private UUID nutritionistId;

    private UUID dietId;

    private List<PatientModel> patients;
}