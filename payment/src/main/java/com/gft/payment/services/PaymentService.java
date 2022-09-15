package com.gft.payment.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gft.payment.dtos.PatientPaymentResponse;
import com.gft.payment.mappers.PaymentMapper;
import com.gft.payment.models.PatientModel;
import com.gft.payment.models.PaymentModel;
import com.gft.payment.repositories.PaymentRepository;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private PatientService patientService;

    // retorna todas as contas de um paciente
    public List<PatientPaymentResponse> getBillByPatientId(UUID id) {
        var payments = paymentRepository.findAllByPatientId(id);
        return payments.stream().map(p -> paymentMapper.toPatientPaymentResponse(p)).toList();
    }

    // paga um determinada conta
    public PatientPaymentResponse toPayBill(UUID id) {

        var bill = paymentRepository.findById(id);

        if (bill.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The bill was not found");
        }
        if (bill.get().getIsPaid()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The bill is paid");
        }
        bill.get().setIsPaid(true);
        bill.get().setPaymentOrderDate(LocalDate.now());
        return paymentMapper.toPatientPaymentResponse(paymentRepository.save(bill.get()));
    }

    public List<PaymentModel> createBills() {
        var currentDate = LocalDate.now();
        var patients = patientService.getAllPatients();
        var bills = paymentRepository.findAll();
        List generatedBills = new ArrayList<PaymentModel>();

        try {
            patients.forEach(patient -> {
                var registrationMonth = patient.getRegistrationDate().getMonthValue();
                var currentMonth = currentDate.getMonthValue();
                var patientBills = paymentRepository.findAllByPatientId(patient.getId());
                if (checkInitialRegistrationWithoutBill(registrationMonth, currentMonth, patientBills))
                    savePayment(patient, currentDate, generatedBills);
                patientBills.forEach(bill -> {
                    if (checkIfPaymentDueDate(bill, currentDate))
                        savePayment(patient, currentDate, generatedBills);
                });
            });
        } catch (Exception e) {
        }
        return generatedBills;
    }

    // checa se o mês atual é dezembro se for retorna o próximo ano senão o ano
    // atual
    public int getNextYearIfDecemberOrCurrentYear(LocalDate currentDate) {
        return (currentDate.getMonthValue() == 12) ? currentDate.getYear() + 1 : currentDate.getYear();
    }

    public int getNextMonth(LocalDate currentDate) {
        return (currentDate.getMonthValue() == 12) ? 1 : currentDate.getMonthValue() + 1;
    }

    public boolean checkIfPaymentDueDate(PaymentModel bill, LocalDate currentDate) {
        return (bill.getPaymentDueDate().getDayOfMonth() == currentDate.getDayOfMonth()
                && bill.getPaymentDueDate().getMonth() == currentDate.getMonth()
                && bill.getPaymentDueDate().getYear() == currentDate.getYear());
    }

    public void savePayment(PatientModel patient, LocalDate currentDate, List<PaymentModel> generatedBills) {
        var payment = new PaymentModel(UUID.randomUUID(), patient.getId(), null, BigDecimal.valueOf(19.90),
                convertMonth(currentDate.getMonthValue()),
                LocalDate.of(getNextYearIfDecemberOrCurrentYear(currentDate),
                        getNextMonth(currentDate), currentDate.getDayOfMonth()),
                null, false);
        generatedBills.add(paymentRepository.save(payment));
    }

    // checa se foi o primeiro registro e se o paciente não tem uma conta gerada
    public boolean checkInitialRegistrationWithoutBill(int registrationMonth, int currentMonth,
            List<PaymentModel> patientBills) {
        return (registrationMonth == currentMonth && patientBills.isEmpty());
    }

    public String convertMonth(int month) {
        switch (month) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                throw new RuntimeException("The number is not valid");

        }
    }
}
