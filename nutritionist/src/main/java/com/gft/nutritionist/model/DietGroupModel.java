package com.gft.nutritionist.model;

import javax.persistence.ManyToOne;

import org.hibernate.annotations.ManyToAny;

public class DietGroupModel {
    private String name;
    private String description;
    private String ctrlParamName;
    private double ctrlParamMinVal;
    private double ctrlParamMaxVal;
    private NutritionistModel nutritionist;
}
