package com.gft.nutritionist.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tb_nutritionist")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class NutritionistModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private String crnNumber;

    @NotNull(message = "O campo e-mail é obrigatório!")
    @Email(message = "O campo Usuário deve ser um e-mail válido!")
    @Column(unique = true)
    private String email;

    @NotNull
    @Size(min = 8, max = 64)
    private String password;

    @NotNull
    private String dietType;

    @Transient
    List<DietGroupModel> dietGroups;

    @Transient
    List<DietModel> diets;

    private String role = "NUTRITIONIST";
}