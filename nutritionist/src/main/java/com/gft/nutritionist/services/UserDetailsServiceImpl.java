package com.gft.nutritionist.services;

import java.util.List;
import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.gft.nutritionist.data.NutritionistDetails;
import com.gft.nutritionist.model.NutritionistModel;
import com.gft.nutritionist.model.Roles;
import com.gft.nutritionist.repository.NutritionistRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final NutritionistRepository nutritionistRepository;
    @Autowired
    private PatientService patientService;

    public UserDetailsServiceImpl(NutritionistRepository nutritionistRepository) {
        this.nutritionistRepository = nutritionistRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<NutritionistModel> nutritionistModel = nutritionistRepository
                .findByEmailContainingIgnoreCase(username);
        if (nutritionistModel.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }
        return new NutritionistDetails(nutritionistModel.get().getId(), nutritionistModel.get().getEmail(),
                nutritionistModel.get().getPassword(), List.of(new Roles(nutritionistModel.get().getRole())));
    }

}
