package com.gft.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import com.gft.patient.models.PatientModel;
import com.gft.patient.repositories.PatientRepository;
import com.gft.patient.service.PatientService;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    private PatientModel patient1 = new PatientModel(UUID.fromString("076beb2a-20eb-4cc2-951e-6c121d714f2b"), "Lairton",
            "Lairton@gmail.com", "12345678",
            Double.valueOf(1.75), 65.2, 22, 0.65, "fasdf", "free",
            "fgkasdfasd", null, UUID.randomUUID(), UUID.randomUUID(), "patient");

    private PatientModel patient2 = new PatientModel(UUID.fromString("15648ea9-6482-4b78-9d61-48285a026348"), "Lairton",
            "Lairton@gmail.com", "12345678",
            Double.valueOf(1.75), 65.2, 22, 0.65, "fasdf", "free",
            "fgkasdfasd", null, UUID.randomUUID(), UUID.randomUUID(), "patient");

    @Test
    public void registerPatientTest() {
        when(patientRepository.save(patient1)).thenReturn(patient1);
        when(patientRepository.findByEmailContainingIgnoreCase(patient1.getEmail())).thenReturn(Optional.empty());

        var patientResponse = patientService.registerPatient(patient1);
        var thrown = assertThrows(ResponseStatusException.class, () -> {
            when(patientRepository.findByEmailContainingIgnoreCase(patient1.getEmail()))
                    .thenReturn(Optional.of(new PatientModel()));
            patientService.registerPatient(patient1);
        });
        assertEquals(patientResponse.getStatusCode(),
                ResponseEntity.status(HttpStatus.CREATED).build().getStatusCode());

        assertEquals(thrown.getStatus(), ResponseEntity.badRequest().build().getStatusCode());
    }

    @Test
    public void checkIfPatientExistsTest() {
        when(patientRepository.findByEmailContainingIgnoreCase("lairton@gmail.com")).thenReturn(Optional.of(patient1));
        when(patientRepository.findByEmailContainingIgnoreCase("Andrei@gmail.com")).thenReturn(Optional.empty());

        var ExistPatient = patientService.checkIfPatientExists("lairton@gmail.com");
        var NoExistPatient = patientService.checkIfPatientExists("Andrei@gmail.com");

        assertTrue(ExistPatient);
        assertFalse(NoExistPatient);
    }

    @Test
    public void updatePatientTest() {
        when(patientRepository.findById(UUID.fromString("076beb2a-20eb-4cc2-951e-6c121d714f2b")))
                .thenReturn(Optional.of(patient1));
        when(patientRepository.findByEmailContainingIgnoreCase(patient1.getEmail())).thenReturn(Optional.of(patient1));
        var response = patientService.updatePatient(patient1);
        assertEquals(ResponseEntity.ok(patient1).getStatusCode(), response.getStatusCode());
    }
}
