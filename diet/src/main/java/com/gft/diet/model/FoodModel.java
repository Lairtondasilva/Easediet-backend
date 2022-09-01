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
    private double sugarG;

    private double fiberG;

    private double servingSizeG;

    private double sodiumMg;

    private double name;

    private double potassiumMg;

    private double fatSaturatedG;

    private double fatTotalG;

    private double calories;

    private double cholesterolMg;

    private double proteinG;

    private double carbohydratesTotalG;

    private double amount;
}
