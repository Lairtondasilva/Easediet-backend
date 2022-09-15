package com.gft.dietsgroups.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gft.dietsgroups.repositories.DietGroupRepository;
import com.gft.dietsgroups.services.PatientService;

@ExtendWith(MockitoExtension.class)
public class DietGroupControllerTest {

    //A Controller depende da Repository para funcionar
    @Mock   //Autowired do Mockito
    private DietGroupRepository dietGroupRepositoryTest;
    
    //A Service também é um Mock neste caso, porque a Controller depende dela pra funcionar
    @Mock
    private PatientService patientServiceTest;

    @InjectMocks    //A classe em que eu injeto é a que eu quero testar
    private DietGroupController dietGroupController;



}
