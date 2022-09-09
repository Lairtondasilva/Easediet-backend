package com.gft.diet.dtos;

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
public class TranslateDietDTO {
    private String breakfastLiquid;

    private String breakfastSolid;

    private String breakfastFruit;

    private String lunchSideDish;

    private String lunchProtein;

    private String lunchSalad;

    private String dinnerSideDish;

    private String dinnerProtein;

    private String dinnerSalad;
}
