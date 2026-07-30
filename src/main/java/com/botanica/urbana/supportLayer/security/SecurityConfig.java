package com.botanica.urbana.supportLayer.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Clase de configuración principal de Spring Security.
 * Establece el control de acceso basado en roles (RBAC), rutas públicas,
 * comportamiento del formulario de login y cierre de sesión (logout).
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Configura la cadena de filtros de seguridad (SecurityFilterChain) de la aplicación.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .userDetailsService(customUserDetailsService)
            .authorizeHttpRequests(auth -> auth
                // Rutas públicas de recursos estáticos (CSS, JS, Imágenes)
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "/favicon.ico").permitAll()
                
                // Rutas públicas de autenticación (Login y Registro)
                .requestMatchers("/login", "/register", "/auth/**").permitAll()
                
                // Rutas públicas del catálogo y consulta de productos
                .requestMatchers("/", "/products", "/products/**").permitAll()
                
                // Rutas exclusivas para el Administrador (CRUD de productos - Requisito 2)
                .requestMatchers("/admin/**").hasRole("ADMIN")
                
                // Rutas del carrito de compras (Requiere estar autenticado - Requisito 3)
                .requestMatchers("/cart/**").hasAnyRole("USER", "ADMIN")
                
                // Cualquier otra solicitud requiere estar autenticado
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("username") // En el formulario de Thymeleaf, name="username"
                .passwordParameter("password")
                .defaultSuccessUrl("/products", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }
}