package com.artisa.artisa.service;

import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public interface ClientService {
    ResponseEntity<Resource> getProfilePicture( Integer id);
}
