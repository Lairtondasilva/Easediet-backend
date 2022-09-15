package com.gft.patient.models;

import java.util.UUID;


import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonNaming(SnakeCaseStrategy.class)
public class FoodModel {

    @EqualsAndHashCode.Include
    private UUID id;

    private Double sugarG;

    private Double fiberG;

    private Integer servingSizeG;

    private Double sodiumMg;

    private String name;

    private Double potassiumMg;

    private Double fatSaturatedMg;

    private Double fatTotalG;

    private Double calories;

    private Double cholesterolMg;

    private Double proteinG;

    private Double carbohydratesTotalG;

}
