package com.artisa.artisa.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(
        @NotBlank(message = "Nom complet is required.")
        String nomComplet,

        @NotBlank(message = "Mot de passe is required.")
        String motDePasse
) {}
