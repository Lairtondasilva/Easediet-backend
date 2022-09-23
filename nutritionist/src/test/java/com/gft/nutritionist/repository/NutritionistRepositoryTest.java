package com.gft.nutritionist.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gft.nutritionist.model.NutritionistModel;

//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NutritionistRepositoryTest {

    @Autowired
    private NutritionistRepository nutritionistRepositoryTest;


    @BeforeAll
    void start() {
        var nutri1 = new NutritionistModel(
                UUID.randomUUID(),
                "Miriam Pacheco",
                "111111",
                "miriam.pacheco@nutri.com",
                "12345678",
                "Healthy", null, null, "NUTRITIONIST");

        nutritionistRepositoryTest.save(nutri1);
    }

    @Test
    @DisplayName("Search for a specific e-mail")
    public void checkIfNutritionistExistTest() {

        Optional<NutritionistModel> nutritionistModelTest = nutritionistRepositoryTest
                .findByEmailContainingIgnoreCase("miriam.pacheco@nutri.com");
        assertEquals("Miriam Pacheco", nutritionistModelTest.get().getName());
    }
}
