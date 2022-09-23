package com.gft.nutritionist.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gft.nutritionist.data.NutritionistDetails;
import com.gft.nutritionist.exception.TokenRefreshException;
import com.gft.nutritionist.model.NutritionistModel;
import com.gft.nutritionist.model.RefreshToken;
import com.gft.nutritionist.model.UserLogin;
import com.gft.nutritionist.payload.JWTResponse;
import com.gft.nutritionist.payload.TokenRefreshRequest;
import com.gft.nutritionist.payload.TokenRefreshResponse;
import com.gft.nutritionist.repository.NutritionistRepository;
import com.gft.nutritionist.services.DietGroupService;
import com.gft.nutritionist.services.DietService;
import com.gft.nutritionist.services.NutritionistService;
import com.gft.nutritionist.services.RefreshTokenService;
import com.gft.nutritionist.util.JwtUtil;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Nutritionist Endpoint")
@RestController
@RequestMapping("/nutritionist")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NutritionistController {

    @Autowired
    private NutritionistService nutritionistService;

    @Autowired
    private NutritionistRepository nutritionistRepository;

    @Autowired
    private DietGroupService dietGroupService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DietService dietService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "Login Nutritionist")
    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody UserLogin userLogin) {
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword(),
                                null));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtil.generateJwtToken(authentication);
        NutritionistDetails userDetails = (NutritionistDetails) authentication.getPrincipal();
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

    @Operation(summary = "FindAll Nutritionists")
    @GetMapping("/all")
    public List<NutritionistModel> findAll() {
        return nutritionistRepository.findAll();
    }

    @Operation(summary = "Find Nutritionist by email")
    @GetMapping("/email/{email}")
    public Optional<NutritionistModel> findNutritionistByEmail(@PathVariable(name = "email") String email) {
        return nutritionistRepository.findByEmailContainingIgnoreCase(email);
    }

    @Operation(summary = "RefreshToken Nutritionist")
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getNutritionist)
                .map(nutri -> {
                    String token = jwtUtil.generateTokenFromUsername(nutri.getEmail());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                }).orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @Operation(summary = "Find Nutritionists by Id")
    @GetMapping("/{nutritionistId}")
    @Retry(name = "default")
    public ResponseEntity<NutritionistModel> getNutritionistById(@PathVariable UUID nutritionistId) {
        return nutritionistRepository.findById(nutritionistId).map(nutri -> {
            var groups = dietGroupService.findDietsGroupsByNutritionistId(nutri.getId());
            nutri.setDietGroups(groups);
            return ResponseEntity.ok(nutri);
        }).orElse(ResponseEntity.notFound().build());

    }

    @Operation(summary = "Register Nutritionists")
    @PostMapping("/register")
    public ResponseEntity<NutritionistModel> nutritionistRegisterPost(
            @RequestBody NutritionistModel nutritionist) {
        nutritionist.setPassword(passwordEncoder.encode(nutritionist.getPassword()));
        return nutritionistService.registerNutritionist(nutritionist);
    }

    // @PostMapping("/login")

    @Operation(summary = "Update Nutritionists")
    @PutMapping("/update")
    @CircuitBreaker(name = "default")
    @Retry(name = "default")
    public ResponseEntity<NutritionistModel> nutritionistUpdatePut(@Valid @RequestBody NutritionistModel nutritionist) {
        return nutritionistService.updateNutritionist(nutritionist)
                .map(response -> ResponseEntity.ok(response))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Nutritionists by Id")
    @DeleteMapping("/{nutritionistId}")
    @Retry(name = "default")
    public void nutritionistDelete(@PathVariable UUID nutritionistId) {
        refreshTokenService.deleteByNutritionist(nutritionistRepository.findById(nutritionistId).get());
        nutritionistRepository.deleteById(nutritionistId);
    }

}
