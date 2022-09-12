package com.gft.diet.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodList {

    private List<FoodModel> items;

    private Double totalCalories;

    public void totalCalories() {
        this.totalCalories = this.items.stream().map(item -> Double.parseDouble(item.getCalories())).toList().stream()
                .reduce(Double.valueOf(0), (a, b) -> a + b);
    };
}
