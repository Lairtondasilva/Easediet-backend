package com.gft.dietsgroups.models;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DietsGroupsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private String ctrlParamName;

    @Column(nullable = false)
    private double ctrlParamMinVal;
    @Column(nullable = false)
    private double ctrlParamMaxVal;

    @Column(name = "nutritionist_id")
    private String nutritionistId;

    @Column(name = "diet_id")
    private String dietId;

    @Transient
    private List<PatientModel> patients;

}
