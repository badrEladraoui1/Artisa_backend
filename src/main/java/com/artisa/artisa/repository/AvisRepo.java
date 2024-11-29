package com.artisa.artisa.repository;

import com.artisa.artisa.entity.Avis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvisRepo extends JpaRepository<Avis, Integer> {
}
