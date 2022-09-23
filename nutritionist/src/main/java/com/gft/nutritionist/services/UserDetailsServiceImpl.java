package com.gft.nutritionist.services;

import java.util.List;
import java.util.Optional;

<<<<<<< HEAD

=======
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> b4e158939f81838224d883bb150d9789a8eaecf4
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.gft.nutritionist.data.NutritionistDetails;
import com.gft.nutritionist.model.NutritionistModel;
import com.gft.nutritionist.model.Roles;
import com.gft.nutritionist.repository.NutritionistRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final NutritionistRepository nutritionistRepository;

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
