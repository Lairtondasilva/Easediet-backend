package com.gft.dietsgroups.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.dietsgroups.models.DietsGroupsModel;

public interface DietsGroupsRepository extends JpaRepository<DietsGroupsModel, String> {
    DietsGroupsModel findDietsGroupsModelByNutritionistId(String id);
}
