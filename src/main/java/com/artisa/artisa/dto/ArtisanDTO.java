package com.artisa.artisa.dto;

import java.util.List;

public record ArtisanDTO(
        Integer id,
        String nomComplet,
        String email,
        String phone,
        String adresse,
        List<String> roles
) {}
