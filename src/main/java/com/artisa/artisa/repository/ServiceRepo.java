package com.artisa.artisa.repository;

import com.artisa.artisa.dto.ServicePreviewDTO;
import com.artisa.artisa.entity.Artisan;
import com.artisa.artisa.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepo extends JpaRepository<Service, Integer> {
    List<Service> findAllByArtisan_Id(Integer artisanId);
    List<Service> findTop2ByArtisanOrderByDateCreationDesc(Artisan artisan);
}
