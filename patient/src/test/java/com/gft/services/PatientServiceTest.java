package com.gft.services;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gft.patient.models.PatientModel;
import com.gft.patient.repositories.PatientRepository;
import com.gft.patient.service.PatientService;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    PatientModel patient = new PatientModel(UUID.randomUUID(), "Lairton", "Lairton@gmail.com", "12345678",
            Double.valueOf(1.75), 65.2, 22, 0.65, "fasdf", "free",
            "fgkasdfasd", null, UUID.randomUUID(), UUID.randomUUID());

    @Test
    public void registerPatientTest() {

    }
}
