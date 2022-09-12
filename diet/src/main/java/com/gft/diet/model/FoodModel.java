package com.gft.diet.model;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

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
@JsonNaming(SnakeCaseStrategy.class)
public class FoodModel {

    private String sugarG;

    private String fiberG;

    private String servingSizeG;

    private String sodiumMg;

    private String name;

    private String potassiumMg;

    private String fatSaturatedG;

    private String fatTotalG;

    private String calories;

    private String cholesterolMg;

    private String proteinG;

    private String carbohydratesTotalG;

    private String amount;
}
