package com.gft.diet.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tb_diet")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class DietModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private UUID id;

    private String name;

    @NotNull
    private String breakfastLiquid;

    private String breakfastSolid;

    private String breakfastFruit;

    @NotNull
    private String lunchSideDish;

    private String lunchProtein;

    private String lunchSalad;

    @NotNull
    private String dinnerSideDish;

    private String dinnerProtein;

    private String dinnerSalad;

    private double caloriesTotalAmount;

    @Transient
    private List<FoodModel> foods;

    private UUID nutritionistId;

    private UUID dietGroupId;
}
