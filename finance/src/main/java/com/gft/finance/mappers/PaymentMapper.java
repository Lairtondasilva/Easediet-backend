package com.gft.finance.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.finance.dtos.PatientPaymentResponse;
import com.gft.finance.models.NutritionistModel;
import com.gft.finance.models.PaymentModel;
import com.gft.finance.services.NutritionistService;
import com.gft.finance.services.PatientService;

@Service
public class PaymentMapper {

    @Autowired
    private PatientService patientService;

    @Autowired
    private NutritionistService nutritionistService;

    /**
     * @param payment
     * @return
     */
    public PatientPaymentResponse toPatientPaymentResponse(PaymentModel payment) {
        return PatientPaymentResponse.builder()
                .id(payment.getId())
                .patientName(patientService.getPatientById(payment.getPatientId()).getName())
                .nutritionistName(nutritionistService.getNutritionistById(payment.getNutritionistId()).getName())
                .billValue(payment.getMonthlyFee())
                .referenceMonth(payment.getReferenceMonth())
                .paymentDueDate(payment.getPaymentDueDate())
                .paymentOrderDate(payment.getPaymentOrderDate())
                .status((payment.getIsPaid()) ? "Pago" : "em aberto").build();
    }
}
