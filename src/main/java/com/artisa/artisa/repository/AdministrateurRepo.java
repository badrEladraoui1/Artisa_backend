package com.artisa.artisa.repository;

import com.artisa.artisa.entity.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministrateurRepo extends JpaRepository<Administrateur, Integer> {
}
