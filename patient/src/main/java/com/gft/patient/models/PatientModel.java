package com.gft.patient.models;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    @Size(min = 8, max = 64)
    private String password;

    @Column(name = "height", nullable = false)
    private Double height;

    @Column(name = "muscle_mass")
    private Double muscleMass;

    @Column(name = "age")
    private Integer age;

    @Column(name = "active_rate")
    private Double activeRate;

    @Column(name = "alergy_intolerance")
    private String alergyOrIntolerance;

    @Column(name = "licence_type")
    private String licenceType;

    @Column(name = "income_profile")
    private String incomeProfile;

    @OneToOne(cascade = CascadeType.ALL)
    @Transient
    private DietModel diet;

    @OneToOne(cascade = CascadeType.ALL)
    @Transient
    private DietGroupModel dietGroup;
}
