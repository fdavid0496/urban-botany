package com.botanica.urbana.supportLayer.security;

import com.botanica.urbana.dataAccessLayer.repository.UserRepository;
import com.botanica.urbana.domainLayer.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Servicio de seguridad personalizado que implementa UserDetailsService de
 * Spring Security.
 * Carga el usuario desde la base de datos MySQL por su correo electrónico y
 * construye
 * el objeto UserDetails necesario para la autenticación y autorización por
 * roles.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuario no encontrado con el correo electrónico: " + username));

        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(userEntity.getRole().getName()));

        return new User(
                userEntity.getEmail(),
                userEntity.getPassword(),
                authorities);
    }
}