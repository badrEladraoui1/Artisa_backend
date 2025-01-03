package com.artisa.artisa.repository;

import com.artisa.artisa.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepo extends JpaRepository<Client, Integer> {
    boolean existsByNomComplet(String nomComplet);
    boolean existsByEmail(String email);
    Optional<Client> findByEmail(String email);
    Optional<Client> findByNomComplet(String nomComplet);
}
