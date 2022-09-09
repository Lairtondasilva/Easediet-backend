package com.gft.diet.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.diet.model.DietModel;
import com.gft.diet.service.TranslateService;
import com.gft.diet.translation.RespText;

@RestController
@RequestMapping("/translate")
@CrossOrigin(origins="*", allowedHeaders="*")
public class TranslateController {

    @Autowired
    private TranslateService translateService;

    @PostMapping("/{sourceLang}/{targetLang}")
    public List<RespText> postTranslation(@PathVariable String sourceLang, @PathVariable String targetLang, @RequestBody DietModel diet) throws IOException, InterruptedException{
        return translateService.translate(sourceLang,targetLang,translateService.generateSourceText(diet));
        //Enviar dados traduzidos para a API CalorieNinjas
    } 
}
