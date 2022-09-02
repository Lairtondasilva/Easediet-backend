package com.gft.diet.model;

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
public class NutritionistModel {

    private UUID id;

    private String name;

    private String crnNumber;

    private String email;

    private String password;

    private String dietType;

    private PatientModel patient;
}
