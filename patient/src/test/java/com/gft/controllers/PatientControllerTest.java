package com.gft.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.gft.model.PatientResponses;
import com.gft.patient.controllers.PatientController;
import com.gft.patient.models.PatientModel;
import com.gft.patient.models.Roles;
import com.gft.patient.repositories.PatientRepository;
import com.gft.patient.service.PatientService;

@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Mock
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientController patientController;

    @Test
    public void findAllTest() {
        PatientModel patient = new PatientModel(UUID.fromString("15648ea9-6482-4b78-9d61-48285a026348"), "Lairton",
                "Lairton@gmail.com", "12345678",
                Double.valueOf(1.75), 65.2, 22, 0.65, "fasdf", "free",
                "fgkasdfasd", null, UUID.randomUUID(), UUID.randomUUID(), new Roles("PATIENT"));
        when(patientService.getAllPatients()).thenReturn(ResponseEntity.ok(List.of(patient)));

        List<PatientModel> patients = new ArrayList();

        var response = new RestTemplate().getForEntity(
                "http://localhost:8082/patient/all",
                patients.getClass());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        // assertEquals(String.class, response.getBody().get(0).getClass());

        String expectedJson = "[{id=1461ed23-0be1-40a7-a92b-ec29d8d65a9a, name=lairton, email=lairton@gmail.com, password=$2a$10$kQBfTHdfZmokfi/hFLtSJOMfzi8CGDF4JKj1kOM4ZwG3IYUuu2/zy, height=1.75, muscleMass=102.0, age=22, activeRate=0.6, alergyOrIntolerance=nenhuma, licenseType=free, incomeProfile=free, registrationDate=2022-09-20, dietId=null, groupId=null, roles={roleName=PATIENT, authority=PATIENT}}]";
        assertEquals(expectedJson, response.getBody().toString());
    }

}
