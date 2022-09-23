package com.gft.payment.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.payment.models.PaymentModel;

public interface PaymentRepository extends JpaRepository<PaymentModel, UUID> {
    public List<PaymentModel> findAllByPatientId(UUID id);

    public List<PaymentModel> findAllByNutritionistId(UUID id);
}