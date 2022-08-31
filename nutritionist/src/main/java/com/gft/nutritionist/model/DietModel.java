package com.gft.nutritionist.model;

import java.util.List;
import java.util.UUID;

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

public class DietModel {

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

    private double caloriesTotalAmount;

    private List<FoodModel> food;

    private NutritionistModel nutritionist;
}
