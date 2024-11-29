package com.artisa.artisa.repository;

import com.artisa.artisa.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepo extends JpaRepository<Utilisateur, Integer> {
    Utilisateur findByNomComplet(String nomComplet);
    boolean existsByNomComplet(String nomComplet);
}
