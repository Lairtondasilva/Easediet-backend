package com.gft.nutritionist.payload;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class JWTResponse {
    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private String email;
    private List<String> roles;

    public JWTResponse(String accessToken, String refreshToken, String email,
            List<String> roles) {
        this.token = accessToken;
        this.refreshToken = refreshToken;
        this.email = email;
        this.roles = roles;
    }

    // getters and setters
}
