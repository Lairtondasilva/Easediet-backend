package com.gft.diet.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.gft.diet.dtos.TranslateDietDTO;
import com.gft.diet.model.DietModel;
import com.gft.diet.translation.RespDat;
import com.gft.diet.translation.RespText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class TranslateService {

    public RespText translate(String sourceLang, String targetLang, String sourceText)
            throws IOException, InterruptedException {
        String sourceExpression = "q=%s&target=%s&source=%s";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://google-translate1.p.rapidapi.com/language/translate/v2"))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("Accept-Encoding", "application/gzip")
                .header("X-RapidAPI-Key", "6b05740cb7msh26efe1ddd81ed82p1cd22ajsne7e7f309089c")
                .header("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
                .method("POST",
                        HttpRequest.BodyPublishers
                                .ofString(String.format(sourceExpression, sourceText, targetLang, sourceLang)))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        RespDat resp = gson.fromJson(response.body(), RespDat.class);
        RespText translatedResponse = resp.getData().getTranslations().get(0);

        return translatedResponse;
    }

    public String generateSourceText(DietModel diet) {
        TranslateDietDTO translateDietDTO = new TranslateDietDTO();

        // Preencher o DTO com as Strings de itens da DietModel
        translateDietDTO.setBreakfastLiquid(diet.getBreakfastLiquid());
        translateDietDTO.setBreakfastSolid(diet.getBreakfastSolid());
        translateDietDTO.setBreakfastFruit(diet.getBreakfastFruit());

        translateDietDTO.setLunchSideDish(diet.getLunchSideDish());
        translateDietDTO.setLunchProtein(diet.getLunchProtein());
        translateDietDTO.setLunchSalad(diet.getLunchSalad());

        translateDietDTO.setDinnerSideDish(diet.getDinnerSideDish());
        translateDietDTO.setDinnerProtein(diet.getDinnerProtein());
        translateDietDTO.setDinnerSalad(diet.getDinnerSalad());

        Stream<String> translateDietDTOStream = Stream.of(
                translateDietDTO.getBreakfastLiquid(),
                translateDietDTO.getBreakfastSolid(),
                translateDietDTO.getBreakfastFruit(),
                translateDietDTO.getLunchSideDish(),
                translateDietDTO.getLunchProtein(),
                translateDietDTO.getLunchSalad(),
                translateDietDTO.getDinnerSideDish(),
                translateDietDTO.getDinnerProtein(),
                translateDietDTO.getDinnerSalad());

        List<String> translateDietDTOStringList = translateDietDTOStream.collect(Collectors.toList());

        String sourceText = String.join(",", translateDietDTOStringList);

        return sourceText;
    }
}
