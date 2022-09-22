package com.gft.nutritionist.model;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Roles implements GrantedAuthority {

    private String roleName;

    @Override
    public String getAuthority() {
        // TODO Auto-generated method stub
        return this.roleName;
    }

}