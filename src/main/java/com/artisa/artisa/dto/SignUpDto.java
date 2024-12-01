package com.artisa.artisa.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignUpDto (
    @NotBlank(message = "Nom complet is required.")
     String nomComplet,

    @Email(message = "Email should be valid and follow the format example@example.abc")
     String email,

    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits.")
     String phone,

    @NotBlank(message = "Mot de passe is required.")
     String motDePasse,

    @Size(max = 100, message = "Address must not exceed 100 characters.")
     String address
){}
