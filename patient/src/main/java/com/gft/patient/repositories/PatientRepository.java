package com.gft.patient.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.patient.models.PatientModel;

@Repository
public interface PatientRepository extends JpaRepository<PatientModel, UUID> {

}
