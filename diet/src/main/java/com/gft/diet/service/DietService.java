package com.gft.diet.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gft.diet.model.DietModel;
import com.gft.diet.repository.DietRepository;

@Service
public class DietService {
    
    @Autowired
    private DietRepository dietRepository;

    public boolean checkIfDietAlreadyExists (String dietName){
        if (dietRepository.findByNameContainingIgnoreCase(dietName).isPresent()){
            return true;
        }
        return false;
    }

    public ResponseEntity<DietModel> dietRegister(DietModel diet){
        if (checkIfDietAlreadyExists(diet.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Already registered diet",null);
        }
        var dietSaved = dietRepository.save(diet);
        return ResponseEntity.status(HttpStatus.CREATED).body(dietSaved);
    }

    public Optional<DietModel> updateDiet (DietModel diet){
        if (dietRepository.findById(diet.getId()).isPresent()){
            List <DietModel> sameNameDiets = dietRepository.findAllByNameContainingIgnoreCase(diet.getName());

            for(DietModel d:sameNameDiets){
                if(d.getNutritionistId().equals(diet.getNutritionistId()) && d.getId()!=diet.getId()){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Diet name already used for this user at the database!");
                }
                return Optional.of(dietRepository.save(diet));
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Diet not found at the database!");
    }

    public String translateText(String sourceText) throws IOException, InterruptedException{
        HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create("https://google-translate1.p.rapidapi.com/language/translate/v2"))
		.header("content-type", "application/x-www-form-urlencoded")
		.header("Accept-Encoding", "application/gzip")
		.header("X-RapidAPI-Key", "13ff71b540msh2b4d7f91e9b5ff4p15c0edjsne5ec396397c1")
		.header("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
		.method("POST", HttpRequest.BodyPublishers.ofString(sourceText))
		.build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        
        return response.body();

    }
}
