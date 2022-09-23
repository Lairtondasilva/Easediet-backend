package com.gft.patient.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.gft.patient.data.PatientDetailsData;
import com.gft.patient.exception.TokenRefreshException;
import com.gft.patient.models.PatientModel;
import com.gft.patient.models.RefreshToken;
import com.gft.patient.models.UserLogin;
import com.gft.patient.payload.JWTResponse;
import com.gft.patient.payload.TokenRefreshRequest;
import com.gft.patient.payload.TokenRefreshResponse;
import com.gft.patient.repositories.PatientRepository;
import com.gft.patient.service.PatientService;
import com.gft.patient.service.RefreshTokenService;
import com.gft.patient.util.JwtUtil;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Nutritionist Endpoint")
@RestController
@RequestMapping("/patient")
@CircuitBreaker(name = "default")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private PatientRepository patientRepository;


    @Operation(summary = "Login Patient")
    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody UserLogin userLogin) {
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword(),
                                null));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);
        PatientDetailsData userDetails = (PatientDetailsData) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        RefreshToken tokenRefresh = refreshTokenService.createRefreshToken(userDetails.getId());
        var response = new JWTResponse(jwt,
                tokenRefresh.getToken(),
                userDetails.getEmail(),
                roles);
        return ResponseEntity.ok(response);

    }

    @Operation(summary = "Refresh Patient Token")
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getPatient)
                .map(patient -> {
                    String token = jwtUtil.generateTokenFromUsername(patient.getEmail());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @Operation(summary = "Find Patient By Email")
    @GetMapping("/email/{email}")
    public Optional<PatientModel> findByPatientEmail(@PathVariable("email") String email) {
        return patientRepository.findByEmailContainingIgnoreCase(email);
    }

    @Retry(name = "default")
    @Operation(summary = "Find All Patients")
    @GetMapping("/all")
    public ResponseEntity<List<PatientModel>> getAll() {
        return patientService.getAllPatients();
    }

    @Retry(name = "default")
    @Operation(summary = "Find Patient by Id")
    @GetMapping("/{id}")
    public ResponseEntity<PatientModel> getById(@PathVariable UUID id) {
        return patientService.getPatientById(id);
    }


    @GetMapping("/diets-groups/{dietGroupId}")
    @Operation(summary = "Find Patient by Diet Group Id")
    @Retry(name = "default", fallbackMethod = "getAllByGroupIdFail")
    public ResponseEntity<List<PatientModel>> getAllByGroupId(@PathVariable UUID dietGroupId) {
        // var resp = new RestTemplate().getForEntity("http://localhost:8080/food",
        // String.class);
        return patientService.getAllPatientsByGroupId(dietGroupId);
    }

    public ResponseEntity<PatientModel> getAllByGroupIdFail(Exception e) {
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "Register a Patient")
    @PostMapping("/register")
    public ResponseEntity<PatientModel> registerPatient(@RequestBody @Valid PatientModel patient) {
        return patientService.registerPatient(patient);
    }

    @Operation(summary = "Update info of a Patient")
    @PutMapping
    public ResponseEntity<PatientModel> updatePatient(@RequestBody @Valid PatientModel patient) {
        return patientService.updatePatient(patient);
    }

    @Operation(summary = "Delete a Patient")
    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable UUID id) {
        patientService.delete(id);
    }
}
