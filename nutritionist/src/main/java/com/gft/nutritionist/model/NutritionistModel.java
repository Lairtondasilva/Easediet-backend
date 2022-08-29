package com.gft.nutritionist.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
public class NutritionistModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private UUID id;
    
    @NotNull
    private String name;

    @NotNull
    private String crnNumber;

	@NotNull(message = "O campo e-mail é obrigatório!")
	@Email(message = "O campo Usuário deve ser um e-mail válido!")
	private String email;

    @NotNull
	@Size(min = 8,max = 64)
	private String password;

    @NotNull
	private String dietType;

    @OneToMany (mappedBy = "nutritionistModel",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("nutritionistModel")
    private List<DietModel> diets;

    @OneToMany (mappedBy = "nutritionistModel",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("nutritionistModel")
    private List<DietGroupModel> dietGroup;

}