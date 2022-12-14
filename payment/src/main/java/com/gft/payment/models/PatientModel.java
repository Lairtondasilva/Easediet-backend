package com.gft.payment.models;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class PatientModel {

    @EqualsAndHashCode.Include
    private UUID id;

    private String name;

    private String email;

    private String password;

    private Double height;

    private Double muscleMass;

    private Integer age;

    private Double activeRate;

    private String alergyOrIntolerance;

    private String licenseType;

    private String incomeProfile;

    private LocalDate registrationDate;

    private UUID dietId;

    private UUID groupId;
}
