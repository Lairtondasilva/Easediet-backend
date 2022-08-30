package com.gft.nutritionist.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.nutritionist.model.NutritionistModel;

public interface NutritionistRepository extends JpaRepository <NutritionistModel,UUID> {
    public Optional<NutritionistModel> findByEmailContainingIgnoreCase (String email);
}
