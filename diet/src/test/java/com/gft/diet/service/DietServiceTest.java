package com.gft.diet.service;

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

import com.gft.diet.model.DietModel;
import com.gft.diet.repository.DietRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
public class DietServiceTest {

    private DietModel diet1 = new DietModel(UUID.fromString("076beb2a-20eb-4cc2-951e-6c121d714f2b"),
        "Pra jogar basquete",
        "Café",
        null,
        null,
        "Arroz",
        null,
        null,
        "Feijão",
        null,
        null,
        2.50,
        null,
        UUID.randomUUID(),
        UUID.randomUUID());

    @Mock
    private DietRepository dietRepository;

    @InjectMocks
    private DietService dietService;

    @Test
    public void dietRegisterTest(){
        when(dietRepository.save(diet1)).thenReturn(diet1);
        when(dietRepository.findByNameContainingIgnoreCase(diet1.getName())).thenReturn(Optional.empty());

        var dietResponse = dietService.registerDiet(diet1);
        var throwsResponse = assertThrows(ResponseStatusException.class, () -> {
            when(dietRepository.findByNameContainingIgnoreCase(diet1.getName()))
            .thenReturn(Optional.of(new DietModel()));
            dietService.registerDiet(diet1);
        });
        assertEquals(dietResponse.getStatusCode(),
                ResponseEntity.status(HttpStatus.CREATED).build().getStatusCode());

        assertEquals(throwsResponse.getStatus(), ResponseEntity.badRequest().build().getStatusCode());

    }

    @Test
    public void checkIfDietExistsTest() {
        when(dietRepository.findByNameContainingIgnoreCase("Pra jogar basquete")).thenReturn(Optional.of(diet1));
        when(dietRepository.findByNameContainingIgnoreCase("Pra jogar futebol")).thenReturn(Optional.empty());

        var ExistingDiet = dietService.checkIfDietAlreadyExists("Pra jogar basquete");
        var NoExistingDiet = dietService.checkIfDietAlreadyExists("Pra jogar futebol");

        assertTrue(ExistingDiet);
        assertFalse(NoExistingDiet);
    }
}
