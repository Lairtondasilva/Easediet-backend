package com.gft.diet.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class DietGroupModel {
    
    private String id;
    
    private String name;
    
    private String description;
    
    private String ctrlParamName;
    
    private double ctrlParamMinVal;
    
    private double ctrlParamMaxVal;
   
    private NutritionistModel nutritionist;
   
    private List<PatientModel> patient;
}
