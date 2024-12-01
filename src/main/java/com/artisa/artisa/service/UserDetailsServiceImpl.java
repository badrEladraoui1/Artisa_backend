

package com.artisa.artisa.service;

import com.artisa.artisa.entity.Utilisateur;
import com.artisa.artisa.repository.UtilisateurRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UtilisateurRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String nomComplet) throws UsernameNotFoundException {
        Utilisateur user = userRepository.findByNomComplet(nomComplet);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return User.builder()
                .username(user.getNomComplet())
                .password(user.getMotDePasse())
                .roles(user.getRoles().stream().map(role -> role.getName()).toArray(String[]::new))
                .build();
    }
}