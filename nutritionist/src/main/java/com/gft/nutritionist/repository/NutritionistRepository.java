package com.gft.nutritionist.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.nutritionist.model.NutritionistModel;

@Repository
public interface NutritionistRepository extends JpaRepository<NutritionistModel, String> {
    Optional<NutritionistModel> findByEmailContainingIgnoreCase(String email);
}
