package com.artisa.artisa.service;

import com.artisa.artisa.dto.ArtisanDetailDTO;
import com.artisa.artisa.dto.ArtisanDisplayDTO;
import com.artisa.artisa.dto.ArtisanProfileDTO;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ArtisanService {
    ResponseEntity<Resource> getProfilePicture(Integer id);
    ResponseEntity<?> updateProfile(Integer id, ArtisanProfileDTO profileDTO);
    List<ArtisanDisplayDTO> getAllArtisans();
    ArtisanDetailDTO getArtisanDetails(Integer artisanId);
}
