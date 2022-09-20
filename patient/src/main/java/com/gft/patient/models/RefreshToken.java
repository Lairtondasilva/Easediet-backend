package com.gft.patient.models;

import java.time.Instant;
import java.util.UUID;

import javax.persistence.*;

import lombok.Data;

@Entity(name = "refreshtoken")
@Data
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID ID;

    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private PatientModel patient;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;

}