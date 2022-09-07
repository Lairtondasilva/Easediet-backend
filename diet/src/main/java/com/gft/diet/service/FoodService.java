package com.gft.diet.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.gft.diet.model.FoodModel;

@Service
public class FoodService {

    public void getFood() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://calorieninjas.p.rapidapi.com/v1/nutrition?query=tomato+banana+cheese"))
                .header("X-RapidAPI-Key", "37c8471f93msha966d36b5d124c8p139af3jsn6df05674191d")
                .header("X-RapidAPI-Host", "calorieninjas.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
