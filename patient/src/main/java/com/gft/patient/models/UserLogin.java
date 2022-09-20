package com.gft.patient.models;

import java.util.List;
import java.util.UUID;

import javax.management.relation.Role;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLogin {

    private UUID id;

    private String email;

    private String password;

    private List<Roles> roles;
}
