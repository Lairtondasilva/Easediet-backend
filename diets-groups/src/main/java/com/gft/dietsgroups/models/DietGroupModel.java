package com.gft.dietsgroups.models;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


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
@Table(name = "tb_diet_group")
public class DietGroupModel {

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
    private UUID nutritionistId;

    @Column(name = "diet_id")
    private UUID dietId;

    @Transient
    private List<PatientModel> patients;

}
