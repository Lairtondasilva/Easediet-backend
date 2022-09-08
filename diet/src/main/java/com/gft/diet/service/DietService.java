package com.gft.diet.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.From;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gft.diet.model.DietModel;
import com.gft.diet.repository.DietRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

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

    public String translateText(String sourceLang, String targetLang, String sourceText) throws IOException, InterruptedException{
        String sourceExpression="q=%s&target=%s&source=%s";
        HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create("https://google-translate1.p.rapidapi.com/language/translate/v2"))
		.header("content-type", "application/x-www-form-urlencoded")
		.header("Accept-Encoding", "application/gzip")
		.header("X-RapidAPI-Key", "14299e8057mshbef33c72ecc61c3p1af833jsn55b9c1af5605")
		.header("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
		.method("POST", HttpRequest.BodyPublishers.ofString(String.format(sourceExpression,sourceText,targetLang,sourceLang)))
		.build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        
        return response.body();
    }
}