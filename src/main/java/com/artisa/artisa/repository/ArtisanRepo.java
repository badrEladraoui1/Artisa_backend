package com.artisa.artisa.repository;

import com.artisa.artisa.entity.Artisan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtisanRepo extends JpaRepository<Artisan, Integer> {
    List<Artisan> findAllByOrderByDateInscriptionDesc();

}
