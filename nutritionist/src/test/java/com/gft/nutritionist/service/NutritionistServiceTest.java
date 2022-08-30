package com.gft.nutritionist.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gft.nutritionist.model.DietGroupModel;
import com.gft.nutritionist.model.DietModel;
import com.gft.nutritionist.model.NutritionistModel;
import com.gft.nutritionist.repository.NutritionistRepository;

@ExtendWith(MockitoExtension.class)
public class NutritionistServiceTest {

    @Mock
    private NutritionistRepository nutritionistRepository;

    @InjectMocks
    private NutritionistService nutritionistService;

    NutritionistModel nutritionistModelTest = new NutritionistModel(UUID.fromString("c81d4e2e-bcf2-11e6-869b-7df92533d2db"),"Miriam Pacheco","111111","miriam.pacheco@nutri.com","12345678","Healthy",null,null);

    @Order(1)
    @Test
    public void checkIfNutritionistExistTest(){
        
    }
}
