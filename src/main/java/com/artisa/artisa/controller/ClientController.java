package com.artisa.artisa.controller;

import com.artisa.artisa.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ClientController {

    ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/{id}/profile-picture")
    public ResponseEntity<Resource> getProfilePicture(@Valid @PathVariable Integer id) {
        return clientService.getProfilePicture(id);
    }


}
