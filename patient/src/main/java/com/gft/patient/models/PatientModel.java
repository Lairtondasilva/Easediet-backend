package com.gft.patient.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@ToString
@Table(name = "tb_patient")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PatientModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "name", nullable = false)
    @NotNull
    @NotBlank
    private String name;

    @Column(name = "email", nullable = false)
    @NotBlank
    @NotNull
    private String email;

    @Column(name = "password", nullable = false)
    @Size(min = 8, max = 64)
    @NotBlank
    private String password;

    @Column(name = "height", nullable = false)
    @NotNull
    private Double height;

    @Column(name = "muscle_mass", nullable = false)
    @NotNull
    private Double muscleMass;

    @Column(name = "age", nullable = false)
    @NotNull
    private Integer age;

    @Column(name = "active_rate")
    private Double activeRate;

    @Column(name = "alergy_intolerance")
    private String alergyOrIntolerance;

    @Column(name = "license_type")
    private String licenseType;

    @Column(name = "income_profile")
    private String incomeProfile;

    @Column(name = "registration_date")
    @CreationTimestamp
    private LocalDate registrationDate;

    @Column(name = "diet_id")
    private UUID dietId;

    @Column(name = "group_id")
    private UUID groupId;

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID();
    }
}
