package com.artisa.artisa.mappers;

import com.artisa.artisa.dto.ArtisanDTO;
import com.artisa.artisa.dto.ServiceResponseDTO;
import com.artisa.artisa.entity.Artisan;
import com.artisa.artisa.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceMapper {

    public ServiceResponseDTO toDTO(com.artisa.artisa.entity.Service service) {
        return new ServiceResponseDTO(
                service.getId(),
                service.getCategorie(),
                service.getDateCreation(),
                service.getDescription(),
                service.getServicePictureFileName(),
                service.getTarif(),
                service.getTitre(),
                toArtisanDTO(service.getArtisan())
        );
    }

    public ArtisanDTO toArtisanDTO(Artisan artisan) {
        List<String> roles = artisan.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        return new ArtisanDTO(
                artisan.getId(),
                artisan.getNomComplet(),
                artisan.getEmail(),
                artisan.getPhone(),
                artisan.getAdresse(),
                roles
        );
    }
}