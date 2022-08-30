package com.gft.nutritionist.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.gft.nutritionist.model.NutritionistModel;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NutritionistRepositoryTest {

    @Autowired
    private NutritionistRepository nutritionistRepositoryTest;

    private NutritionistModel nutritionistModelTest;

    @BeforeAll
    void start(){
        this.nutritionistModelTest = nutritionistRepositoryTest.save(new NutritionistModel (
            UUID.fromString("c81d4e2e-bcf2-11e6-869b-7df92533d2db"),"Miriam Pacheco",
            "111111",
            "miriam.pacheco@nutri.com",
            "12345678",
            "Healthy",
            null,
            null));
    }

    @Order(1)
    @Test
    @DisplayName("Search for a specific e-mail")
    public void checkIfNutritionistExistTest(){
        Optional<NutritionistModel> nutritionistModelTest = nutritionistRepositoryTest.findByEmailContainingIgnoreCase(this.nutritionistModelTest.getEmail());
        assertEquals("Miriam Pacheco",nutritionistModelTest.get().getName());
    }
}
