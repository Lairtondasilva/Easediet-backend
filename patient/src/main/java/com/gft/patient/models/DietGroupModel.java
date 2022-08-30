package com.gft.patient.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class DietGroupModel {
    private String name;
    private String description;
    private String ctrlParamName;
    private double ctrlParamMinVal;
    private double ctrlParamMaxVal;
}
