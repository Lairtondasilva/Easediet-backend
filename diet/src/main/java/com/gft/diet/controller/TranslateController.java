package com.gft.diet.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.diet.model.DietModel;
import com.gft.diet.service.TranslateService;
import com.gft.diet.translation.RespText;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/translate")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@CircuitBreaker(name = "default")
public class TranslateController {

    @Autowired
    private TranslateService translateService;

    @PostMapping("/{sourceLang}/{targetLang}")
    @Retry(name = "default", fallbackMethod = "TranslateFail")
    public RespText postTranslation(@PathVariable String sourceLang, @PathVariable String targetLang,
            @RequestBody DietModel diet) throws IOException, InterruptedException {
        return translateService.translate(sourceLang, targetLang, translateService.generateSourceText(diet));
    }

    public ResponseEntity<String> TranslateFail(Exception e) {
        return ResponseEntity.badRequest().body("Não é possivel traduzir sua dieta no momento");
    }
}
