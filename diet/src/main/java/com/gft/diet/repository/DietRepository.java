package com.gft.diet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.diet.model.DietModel;

public interface DietRepository extends JpaRepository<DietModel,String>{
    public List<DietModel> findByCaloriesTotalAmountLessThanEqual(double caloriesTotalAmount);
    public List<DietModel> findByCaloriesTotalAmountGreaterThanEqual(double caloriesTotalAmount);
    public Optional <DietModel> findByNameContainingIgnoreCase(String name);
    public List <DietModel> findAllByNameContainingIgnoreCase(String name);
    public List<DietModel> findByNutritionistId(String nutritionistId);
}
