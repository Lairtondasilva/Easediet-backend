package com.gft.dietsgroups.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.gft.dietsgroups.models.DietGroupModel;
import com.gft.dietsgroups.models.PatientModel;
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
    private DietsGroupsController dietGroupController;

    @Test
    public void findAllTest() {

        List<DietGroupModel> dietsGroups = new ArrayList<>();

        var response = new RestTemplate().getForEntity(
                "http://localhost:8084/diets-groups/all",
                dietsGroups.getClass());

                assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
