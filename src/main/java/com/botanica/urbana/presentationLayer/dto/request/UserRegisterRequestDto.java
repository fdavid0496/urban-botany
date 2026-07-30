package com.botanica.urbana.presentationLayer.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO que captura y valida la información enviada desde el formulario
 * de registro de nuevos usuarios.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterRequestDto {
    
    /**
     * Nombre completo del usuario.
     */
    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre completo debe tener entre 3 y 100 caracteres")
    private String fullName;

    /**
     * Correo electrónico del usuario (utilizado para el login).
     */
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Debe proporcionar una dirección de correo electrónico válida")
    @Size(max = 120, message = "El correo electrónico no puede exceder los 120 caracteres")
    private String email;

    /**
     * Contraseña seleccionada por el usuario.
     */
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 30, message = "La contraseña debe tener entre 6 y 30 caracteres")
    private String password;

    /**
     * Confirmación de la contraseña para validación de coincidencia.
     */
    @NotBlank(message = "Debe confirmar la contraseña")
    private String confirmPassword;
}
