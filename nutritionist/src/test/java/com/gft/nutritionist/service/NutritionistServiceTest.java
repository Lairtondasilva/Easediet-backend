package com.gft.nutritionist.service;

import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gft.nutritionist.model.NutritionistModel;
import com.gft.nutritionist.model.Roles;
import com.gft.nutritionist.repository.NutritionistRepository;
import com.gft.nutritionist.services.NutritionistService;

@ExtendWith(MockitoExtension.class)
public class NutritionistServiceTest {

    @Mock
    private NutritionistRepository nutritionistRepository;

    @InjectMocks
    private NutritionistService nutritionistService;

    NutritionistModel nutritionistModelTest = new NutritionistModel(UUID.randomUUID(),
            "Miriam Pacheco", "111111", "miriam.pacheco@nutri.com", "12345678", "Healthy", null, null,
            new Roles("NUTRITIONIST"));

    @Order(1)
    @Test
    public void checkIfNutritionistExistTest() {

    }
}
