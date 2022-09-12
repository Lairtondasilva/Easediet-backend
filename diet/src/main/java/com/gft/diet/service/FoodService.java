package com.gft.diet.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.model.CamelCaseSplittingFieldNamingStrategy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.gft.diet.convertion.Conversor;
import com.gft.diet.model.FoodList;
import com.gft.diet.model.FoodModel;
import com.google.gson.Gson;

@Service
public class FoodService {

    @Autowired
    private Conversor conversor;

    public FoodList getFood(List<String> foods) throws IOException, InterruptedException {

        String query = foods.toString().replace("[", "").replace("]", "").replace(", ", "+");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://calorieninjas.p.rapidapi.com/v1/nutrition?query=%s", query)))
                .header("X-RapidAPI-Key", "37c8471f93msha966d36b5d124c8p139af3jsn6df05674191d")
                .header("X-RapidAPI-Host", "calorieninjas.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.ofString("sugar_g"))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        // biblioteca que faz o binding entre a resposta da requisição e a classe do
        // java
        Gson gson = new Gson();

        // fromJson converte o json para uma classe
        FoodList foodList = gson.fromJson(conversor.snakeToCamel(response.body()).toString(), FoodList.class);
        foodList.totalCalories();
        return foodList;
    }
}
