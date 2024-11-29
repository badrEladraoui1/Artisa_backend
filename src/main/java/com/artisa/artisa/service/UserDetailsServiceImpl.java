package com.artisa.artisa.service;

import com.artisa.artisa.entity.Utilisateur;
import com.artisa.artisa.repository.UtilisateurRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
        // Retrieve the user from the database
        Utilisateur user = userRepository.findByNomComplet(nomComplet);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with nomComplet: " + nomComplet);
        }

        // Return the user as UserDetails
        return user; // Since Utilisateur implements UserDetails
    }
}


//package com.artisa.artisa.service;
//
//import com.artisa.artisa.entity.Utilisateur;
//import com.artisa.artisa.repository.UtilisateurRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.stream.Collectors;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private UtilisateurRepo userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String nomComplet) throws UsernameNotFoundException {
//        Utilisateur user = userRepository.findByNomComplet(nomComplet);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
////        return (UserDetails) Utilisateur.builder()
////                .nomComplet(user.getNomComplet())
////                .motDePasse(user.getMotDePasse())
////                .roles((java.util.Set<com.artisa.artisa.entity.Role>) user.getRoles().stream().map(role -> role.getName()))
////                .build();
//
//        return (UserDetails) Utilisateur.builder()
//                .nomComplet(user.getNomComplet())
//                .motDePasse(user.getMotDePasse())
//                .roles(user.getRoles().stream().collect(Collectors.toSet()))
//                .build();
//
////        return Utilisateur.builder()
////                .nomComplet(user.getNomComplet())
////                .motDePasse(user.getMotDePasse())
////                .roles(user.getRoles().stream().map(role -> role.getName()).toArray(String[]::new))
////                .build();
//    }
//}
