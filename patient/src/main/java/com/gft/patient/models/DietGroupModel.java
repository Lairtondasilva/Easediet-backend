package com.gft.patient.models;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class DietGroupModel {
    private UUID id;
    private String name;
    private String description;
    private String ctrlParamName;
    private double ctrlParamMinVal;
    private double ctrlParamMaxVal;
}
