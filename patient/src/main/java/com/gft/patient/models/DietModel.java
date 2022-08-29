package com.gft.patient.models;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DietModel {

    @EqualsAndHashCode.Include
    private UUID id;
    private String breakfastLiquid;

    private String breakfastSolid;

    private String breakfastFruit;

    private String lunchSideDish;

    private String lunchProtein;

    private String lunchSalad;

    private String dinnerSideDish;

    private String dinnerProtein;

    private String dinnerSalad;

    private List<FoodModel> food;

    private Double caloriesTotalAmount;

}
