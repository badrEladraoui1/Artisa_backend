package com.artisa.artisa.service;

import com.artisa.artisa.dto.ArtisanProfileDTO;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public interface ArtisanService {
    ResponseEntity<Resource> getProfilePicture(Integer id);
    ResponseEntity<?> updateProfile(Integer id, ArtisanProfileDTO profileDTO);
}
