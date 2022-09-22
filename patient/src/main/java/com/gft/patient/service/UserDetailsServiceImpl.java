package com.gft.patient.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.gft.patient.data.PatientDetailsData;
import com.gft.patient.models.PatientModel;
import com.gft.patient.models.Roles;
import com.gft.patient.models.UserLogin;
import com.gft.patient.repositories.PatientRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PatientRepository patientRepository;

    public UserDetailsServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<PatientModel> patientModel = patientRepository.findByEmailContainingIgnoreCase(username);
        if (patientModel.isEmpty()) {
            throw new UsernameNotFoundException("Username [" + username + "] not found!!");
        }

        return new PatientDetailsData(patientModel.get().getId(), patientModel.get().getEmail(),
                patientModel.get().getPassword(), List.of(new Roles(patientModel.get().getRole())));
    }

}
