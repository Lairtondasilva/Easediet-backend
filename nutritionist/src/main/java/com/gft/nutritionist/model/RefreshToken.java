package com.gft.nutritionist.model;

import java.time.Instant;
import java.util.UUID;

import javax.persistence.*;

import lombok.Data;

@Entity(name = "refreshtoken_nutritionist")
@Data
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "nutritionist", referencedColumnName = "id")
    private NutritionistModel nutritionist;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;

}