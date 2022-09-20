package com.gft.nutritionist.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLogin {

    private UUID id;

    private String username;

    private String password;

    private String role;
}

