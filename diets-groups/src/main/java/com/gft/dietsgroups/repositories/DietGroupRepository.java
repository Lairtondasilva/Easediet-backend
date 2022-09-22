package com.gft.dietsgroups.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.dietsgroups.models.DietGroupModel;

public interface DietGroupRepository extends JpaRepository<DietGroupModel, UUID> {
    List<DietGroupModel> findAllByNutritionistId(UUID id);
}
