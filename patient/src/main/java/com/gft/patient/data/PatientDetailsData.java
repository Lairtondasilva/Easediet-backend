package com.gft.patient.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.gft.patient.models.PatientModel;

public class PatientDetailsData implements UserDetails {

    private final Optional<PatientModel> patientModel;

    public PatientDetailsData(Optional<PatientModel> patientModel) {
        this.patientModel = patientModel;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return patientModel.orElse(new PatientModel()).getPassword();
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return patientModel.orElse(new PatientModel()).getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

}
