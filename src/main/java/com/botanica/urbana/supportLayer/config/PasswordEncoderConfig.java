package com.botanica.urbana.supportLayer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Clase de configuración para registrar el Bean del encriptador de contraseñas.
 * Se define en una clase independiente para prevenir ciclos de dependencia circular
 * entre Spring Security y los servicios de la aplicación.
 */
@Configuration
public class PasswordEncoderConfig {

    /**
     * Registra el Bean de PasswordEncoder utilizando el algoritmo estándar de encriptación BCrypt.
     *
     * @return Instancia de BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}