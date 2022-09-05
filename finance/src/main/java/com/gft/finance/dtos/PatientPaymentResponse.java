package com.gft.finance.dtos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PatientPaymentResponse {
    private UUID id;
    private String patientName;
    private String nutritionistName;
    private BigDecimal billValue;
    private String referenceMonth;
    private Date paymentDueDate;
    private Date paymentOrderDate;
    private String status;
}
