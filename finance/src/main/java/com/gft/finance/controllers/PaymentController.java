package com.gft.finance.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Formatter.BigDecimalLayoutForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.PluggableSchemaResolver;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.finance.dtos.PatientPaymentResponse;
import com.gft.finance.mappers.PaymentMapper;
import com.gft.finance.models.PatientModel;
import com.gft.finance.models.PaymentModel;
import com.gft.finance.repositories.PaymentRepository;
import com.gft.finance.services.PatientService;
import com.gft.finance.services.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PatientService patientService;

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
    public List<PatientPaymentResponse> getBill(@PathVariable UUID id) {
        return paymentService.getBillByPatientId(id);
    }

    @PutMapping("/pay/{id}")
    public PatientPaymentResponse toPayBill(@PathVariable(name = "id") UUID id) {
        return paymentService.toPayBill(id);
    }

    @PostMapping
    public List<PaymentModel> createBills() {
        var currentDate = LocalDate.of(2022, 10, 07);
        // var currentDate = LocalDate.now();
        var patients = patientService.getAllPatients();
        var bills = paymentRepository.findAll();
        List generatedBills = new ArrayList<PaymentModel>();

        try {
            patients.forEach(patient -> {
                if (patient.getRegistrationDate().getMonthValue() == currentDate.getMonthValue() && bills.size() == 0) {
                    bills.forEach(bill -> {
                        if (!bill.getPatientId().equals(patient.getId())) {
                            var payment = new PaymentModel(UUID.randomUUID(), patient.getId(), null,
                                    BigDecimal.valueOf(19.90),
                                    paymentService.convertMonth(currentDate.getMonthValue()),
                                    LocalDate.of(
                                            (currentDate.getMonthValue() == 12) ? currentDate.getYear() + 1
                                                    : currentDate.getYear(),
                                            (currentDate.getMonthValue() == 12) ? 1 : currentDate.getMonthValue() + 1,
                                            currentDate.getDayOfMonth()),
                                    null, false);
                            generatedBills.add(paymentRepository.save(payment));
                        }
                    });
                }
                List<PaymentModel> patientBills = paymentRepository.findAllByPatientId(patient.getId());
                patientBills.forEach(bill -> {
                    if (bill.getPaymentDueDate().getDayOfMonth() == currentDate.getDayOfMonth()
                            && bill.getPaymentDueDate().getMonth() == currentDate.getMonth()
                            && bill.getPaymentDueDate().getYear() == currentDate.getYear()) {
                        var payment = new PaymentModel(UUID.randomUUID(), patient.getId(), null,
                                BigDecimal.valueOf(19.90),
                                paymentService.convertMonth(currentDate.getMonthValue()),
                                LocalDate.of((currentDate.getMonthValue() == 12) ? currentDate.getYear() + 1
                                        : currentDate.getYear(),
                                        (currentDate.getMonthValue() == 12) ? 1 : currentDate.getMonthValue() + 1,
                                        currentDate.getDayOfMonth()),
                                null, false);
                        generatedBills.add(paymentRepository.save(payment));
                    }
                });

            });
        } catch (Exception e) {

        }
        return generatedBills;
    }

    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable UUID id) {
        paymentRepository.deleteById(id);
    }

}
