package com.gft.nutritionist.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gft.nutritionist.model.NutritionistModel;
import com.gft.nutritionist.repository.NutritionistRepository;

@Service
public class NutritionistService {
    @Autowired
    private NutritionistRepository nutritionistRepository;

    public ResponseEntity<NutritionistModel> registerNutritionist(NutritionistModel nutritionist) {
        if (checkIfNutritionistExists(nutritionist.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already registered e-mail!", null);
        }

        var nutritionistSaved = nutritionistRepository.save(nutritionist);

        return ResponseEntity.status(HttpStatus.CREATED).body(nutritionistSaved);

    }

    public boolean checkIfNutritionistExists(String email) {
        if (nutritionistRepository.findByEmailContainingIgnoreCase(email).isPresent()) {
            return true;
        }
        return false;
    }
    
    public Optional<NutritionistModel> updateNutritionist (NutritionistModel nutritionist) {
        if (nutritionistRepository.findById(nutritionist.getUUID()).isPresent()){
            Optional<NutritionistModel> findNutritionistEmail = nutritionistRepository.findByEmailContainingIgnoreCase(nutritionist.getEmail());

            // Se o email já existe e o ID é diferente, significa que passei o email de outro usuario já cadastrado
            if (findNutritionistEmail.isPresent() && findNutritionistEmail.get().getUUID() != nutritionist.getUUID()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já cadastrado no banco de dados",null);
            }

                nutritionist.setPassword(passwordEncryption(nutritionist.getPassword()));
        }
        return Optional.of(nutritionistRepository.save(nutritionist));
    }
        

    private String passwordEncryption(String password) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.encode(password);

    }

}
