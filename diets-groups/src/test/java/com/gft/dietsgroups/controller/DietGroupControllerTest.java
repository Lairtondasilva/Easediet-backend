package com.gft.dietsgroups.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
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
import com.gft.dietsgroups.repositories.DietGroupRepository;
import com.gft.dietsgroups.services.PatientService;

@ExtendWith(MockitoExtension.class)
public class DietGroupControllerTest {

    // A Controller depende da Repository para funcionar
    @Mock // Autowired do Mockito
    private DietGroupRepository dietGroupRepositoryTest;

    // A Service também é um Mock neste caso, porque a Controller depende dela pra
    // funcionar
    @Mock
    private PatientService patientServiceTest;

    @InjectMocks // A classe em que eu injeto é a que eu quero testar
    private DietsGroupsController dietGroupController;

    @Test
    public void findAllTest() {

        List<DietGroupModel> dietsGroups = new ArrayList<>();

        var response = new RestTemplate().getForEntity(
                "http://localhost:8084/diets-groups/all",
                dietsGroups.getClass());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void createGroup() throws URISyntaxException {

        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:8084/diets-groups";
        URI uri = new URI(baseUrl);

        DietGroupModel dietGroup = new DietGroupModel(
                UUID.randomUUID(), "Grande", "Cresce", "Teste", 1000.5, 855.9, UUID.randomUUID(), UUID.randomUUID(),
                null);

        ResponseEntity<String> result = restTemplate.postForEntity(uri, dietGroup, String.class);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());

    }

}
