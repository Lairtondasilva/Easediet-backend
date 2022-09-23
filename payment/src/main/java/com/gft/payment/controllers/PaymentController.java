package com.gft.payment.controllers;


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

import com.gft.payment.dtos.PatientPaymentResponse;
import com.gft.payment.models.PaymentModel;
import com.gft.payment.repositories.PaymentRepository;
import com.gft.payment.services.PaymentService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Payment Endpoint")
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

    @Operation(summary = "FindAll Payment")
    @GetMapping("/all")
    public List<PaymentModel> getAll() {
        return paymentRepository.findAll();
    }

    @Operation(summary = "Find Payment by Id")
    @GetMapping("/{id}")
    public Optional<PaymentModel> getById(@PathVariable UUID id) {
        return paymentRepository.findById(id);
    }

    @Operation(summary = "Find Bill By Patient Id")
    @GetMapping("/patient/{id}")
    @Retry(name = "default", fallbackMethod = "getBillFail")
    public List<PatientPaymentResponse> getBill(@PathVariable UUID id) {
        return paymentService.getBillByPatientId(id);
    }

    public ResponseEntity<String> getBillFail(Exception e) {
        return ResponseEntity.badRequest().body("The service is currently unavailable.");
    }

    @Operation(summary = "Pay Bill")
    @PutMapping("/pay/{id}")
    public PatientPaymentResponse toPayBill(@PathVariable(name = "id") UUID id) {
        return paymentService.toPayBill(id);
    }

    public ResponseEntity<String> toPayBillFail(Exception e) {
        return ResponseEntity.badRequest().body("Unable to make payment");
    }

    @Operation(summary = "Create Bill")
    @PostMapping
    public List<PaymentModel> createBills() {
        return paymentService.createBills();
    }

    @Operation(summary = "Delete Payment by Id")
    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable UUID id) {
        paymentRepository.deleteById(id);
    }

}
