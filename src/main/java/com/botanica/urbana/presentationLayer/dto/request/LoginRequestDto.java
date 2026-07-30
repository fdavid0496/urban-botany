package com.botanica.urbana.presentationLayer.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO que captura y valida las credenciales ingresadas en el formulario
 * de inicio de sesión (login).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequestDto {
    
    /**
     * Correo electrónico del usuario registrado.
     */
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Debe ingresar una dirección de correo electrónico válida")
    private String email;

    /**
     * Contraseña del usuario.
     */
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
