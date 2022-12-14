package com.gft.patient.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.gft.patient.data.PatientDetailsData;
import com.gft.patient.exception.JwtTokenMalformedException;
import com.gft.patient.exception.JwtTokenMissingException;
import com.gft.patient.repositories.PatientRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtil {

    public static final int tokenValidity = 6_000_000;
    public static final String jwtSecret = "4e21dd7a-d685-4929-a76b-c2f0eb84f3af";
    @Autowired
    private PatientRepository patientRepository;

    public Claims getClaims(final String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return body;
        } catch (Exception e) {
            System.out.println(e.getMessage() + " => " + e);
        }
        return null;
    }

    public String generateJwtToken(Authentication authentication) {

        PatientDetailsData userPrincipal = (PatientDetailsData) authentication.getPrincipal();
        Map<String, Object> roles = new HashMap<>();

        roles.put("role", userPrincipal.getAuthorities().stream().map(t -> t.getAuthority()).toList().get(0));

        return Jwts.builder()
                .setClaims(roles)
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + tokenValidity))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateTokenFromUsername(String username) {
        Map<String, Object> roles = new HashMap<>();
        var userPrincipal = patientRepository.findByEmailContainingIgnoreCase(username).get();
        roles.put("role", userPrincipal.getRole());
        return Jwts.builder()
                .setClaims(roles)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + tokenValidity))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(final String token)
            throws JwtTokenMalformedException, com.gft.patient.exception.JwtTokenMissingException {
        try {

            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;

        } catch (SignatureException ex) {
            throw new JwtTokenMalformedException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new JwtTokenMalformedException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new JwtTokenMalformedException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new JwtTokenMalformedException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new JwtTokenMissingException("JWT claims string is empty.");
        }
    }
}
