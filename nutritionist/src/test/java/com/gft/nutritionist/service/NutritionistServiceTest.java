package com.gft.nutritionist.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import com.gft.nutritionist.model.NutritionistModel;
import com.gft.nutritionist.repository.NutritionistRepository;
import com.gft.nutritionist.services.NutritionistService;

@ExtendWith(MockitoExtension.class)
public class NutritionistServiceTest {

    @Mock
    private NutritionistRepository nutritionistRepository;

    @InjectMocks
    private NutritionistService nutritionistService;

    NutritionistModel nutritionist1 = new NutritionistModel(UUID.randomUUID(),
            "Miriam Pacheco", "111111", "miriam.pacheco@nutri.com", "12345678", "Healthy", null, null,
            "NUTRITIONIST");

    @Test
    public void registerTheSameUserTwice() {
        var nutritionistResponse = nutritionistService.registerNutritionist(nutritionist1);
        var throwsResponse = assertThrows(ResponseStatusException.class, () -> {
            when(nutritionistRepository.findByEmailContainingIgnoreCase(nutritionist1.getEmail()))
            .thenReturn(Optional.of(new NutritionistModel()));
            nutritionistService.registerNutritionist(nutritionist1);
        });
        assertEquals(nutritionistResponse.getStatusCode(),
                ResponseEntity.status(HttpStatus.CREATED).build().getStatusCode());

        assertEquals(throwsResponse.getStatus(), ResponseEntity.badRequest().build().getStatusCode());
    }

    @Test
    public void checkIfNutritionistExistsTest() {
        when(nutritionistRepository.findByEmailContainingIgnoreCase("lairton@gmail.com")).thenReturn(Optional.of(nutritionist1));
        when(nutritionistRepository.findByEmailContainingIgnoreCase("Andrei@gmail.com")).thenReturn(Optional.empty());

        var ExistingNutritionist = nutritionistService.checkIfNutritionistExists("lairton@gmail.com");
        var NoExistingNutritionist = nutritionistService.checkIfNutritionistExists("Andrei@gmail.com");

        assertTrue(ExistingNutritionist);
        assertFalse(NoExistingNutritionist);
    }
}
