package com.gft.finance.services;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gft.finance.dtos.PatientPaymentResponse;
import com.gft.finance.mappers.PaymentMapper;
import com.gft.finance.models.PaymentModel;
import com.gft.finance.repositories.PaymentRepository;
import com.netflix.discovery.converters.Auto;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PaymentMapper paymentMapper;

    public List<PatientPaymentResponse> getBillByPatientId(UUID id) {
        var payments = paymentRepository.findAllByPatientId(id);
        return payments.stream().map(p -> paymentMapper.toPatientPaymentResponse(p)).toList();
    }

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
