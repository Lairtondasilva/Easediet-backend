package com.gft.finance.controllers;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.finance.dtos.PatientPaymentResponse;
import com.gft.finance.models.PaymentModel;
import com.gft.finance.repositories.PaymentRepository;
import com.gft.finance.services.PaymentService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@CircuitBreaker(name = "default")
@Retry(name = "default")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/all")
    public List<PaymentModel> getAll() {
        return paymentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<PaymentModel> getById(@PathVariable UUID id) {
        return paymentRepository.findById(id);
    }

    @GetMapping("/patient/{id}")
    @Retry(name = "default", fallbackMethod = "getBillFail")
    public List<PatientPaymentResponse> getBill(@PathVariable UUID id) {
        return paymentService.getBillByPatientId(id);
    }

    public ResponseEntity<String> getBillFail(Exception e) {
        return ResponseEntity.badRequest().body("The service is currently unavailable.");
    }

    @PutMapping("/pay/{id}")
    public PatientPaymentResponse toPayBill(@PathVariable(name = "id") UUID id) {
        return paymentService.toPayBill(id);
    }

    public ResponseEntity<String> toPayBillFail(Exception e) {
        return ResponseEntity.badRequest().body("Unable to make payment");
    }

    @PostMapping
    public List<PaymentModel> createBills() {
        return paymentService.createBills();
    }

    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable UUID id) {
        paymentRepository.deleteById(id);
    }

}
