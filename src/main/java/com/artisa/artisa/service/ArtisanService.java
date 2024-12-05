package com.artisa.artisa.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public interface ArtisanService {
    ResponseEntity<Resource> getProfilePicture(Integer id);
}
