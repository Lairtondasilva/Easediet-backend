package com.gft.patient.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.patient.models.PatientModel;

@Repository
public interface PatientRepository extends JpaRepository<PatientModel, UUID> {
    Optional<PatientModel> findByEmailContainingIgnoreCase(String email);

    List<PatientModel> findAllByGroupId(UUID id);
}
