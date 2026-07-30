package com.botanica.urbana.domainLayer.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad JPA que representa la tabla 'users' en la base de datos.
 * Almacena los datos de autenticación y perfil de cada usuario.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    
    /**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre completo del usuario.
     */
    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    /**
     * Correo electrónico único (utilizado como username en el login).
     */
    @Column(name = "email", nullable = false, unique = true, length = 120)
    private String email;

    /**
     * Contraseña encriptada con BCrypt.
     */
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    /**
     * Rol asignado al usuario para el control de acceso (ROLE_ADMIN o ROLE_USER).
     * Carga EAGER para disponer de los permisos inmediatamente al autenticar.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    /**
     * Fecha y hora en que se registró el usuario.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Fecha y hora de la última actualización de datos.
     */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
