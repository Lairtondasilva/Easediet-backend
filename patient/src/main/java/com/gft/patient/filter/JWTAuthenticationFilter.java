package com.gft.patient.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.patient.data.PatientDetailsData;
import com.gft.patient.models.PatientModel;
import com.gft.patient.util.JwtUtil;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final int TOKEN_EXPIRES = 600_000;
    public static final String TOKEN_SENHA = "4e21dd7a-d685-4929-a76b-c2f0eb84f3af";

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            PatientModel patientModel = new ObjectMapper()
                    .readValue(request.getInputStream(), PatientModel.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(

                            patientModel.getEmail(),
                            patientModel.getPassword(),
                            new ArrayList<>()));

        } catch (IOException e) {
            throw new RuntimeException("Failed to authenticate user", e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult)
            throws IOException, ServletException {

        PatientDetailsData patientDetailData = (PatientDetailsData) authResult.getPrincipal();

        String token = jwtUtil.generateToken(patientDetailData);
        response.getWriter().write("Bearer " + token);
        response.getWriter().flush();
    }
}
