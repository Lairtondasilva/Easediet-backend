package com.gft.finance.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.finance.dtos.PatientPaymentResponse;
import com.gft.finance.mappers.PaymentMapper;
import com.gft.finance.models.PatientModel;
import com.gft.finance.models.PaymentModel;
import com.gft.finance.repositories.PaymentRepository;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentMapper paymentMapper;

    @GetMapping("/patient/{id}")
    public List<PatientPaymentResponse> getBill(@PathVariable UUID id) {
        var payments = new ArrayList<PaymentModel>();
        var payment = new PaymentModel(UUID.fromString("30e53eea-763d-4aa1-8fe1-738fa2d31c2e"), UUID.randomUUID(),
                UUID.randomUUID(), BigDecimal.valueOf(19.90), "Setembro", null, null, true);
        payments.add(payment);
        // var payments = paymentRepository.findAllByPatientId(id);
        var response = payments.stream().map(p -> paymentMapper.toPatientPaymentResponse(p)).toList();
        return response;
    }
}
