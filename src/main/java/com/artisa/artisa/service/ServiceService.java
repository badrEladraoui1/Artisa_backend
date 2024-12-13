package com.artisa.artisa.service;

import com.artisa.artisa.dto.ServiceDTO;
import com.artisa.artisa.dto.ServiceResponseDTO;
import com.artisa.artisa.entity.Service;

import java.util.List;

public interface ServiceService {
    String createService(String token, ServiceDTO serviceDTO);
    String updateService(Integer serviceId, String token, ServiceDTO serviceDTO);
    String deleteService(Integer serviceId, String token);
    List<ServiceResponseDTO> getArtisanServices(Integer artisanId, String token);

}
