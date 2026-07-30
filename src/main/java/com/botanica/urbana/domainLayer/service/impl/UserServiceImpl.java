package com.botanica.urbana.domainLayer.service.impl;

import com.botanica.urbana.dataAccessLayer.repository.RoleRepository;
import com.botanica.urbana.dataAccessLayer.repository.UserRepository;
import com.botanica.urbana.domainLayer.entity.RoleEntity;
import com.botanica.urbana.domainLayer.entity.UserEntity;
import com.botanica.urbana.domainLayer.service.UserService;
import com.botanica.urbana.presentationLayer.dto.request.UserRegisterRequestDto;
import com.botanica.urbana.presentationLayer.dto.response.UserResponseDto;
import com.botanica.urbana.supportLayer.exception.BadRequestException;
import com.botanica.urbana.supportLayer.exception.ResourceNotFoundException;
import com.botanica.urbana.supportLayer.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación del servicio UserService.
 * Maneja la lógica de negocio para la gestión de usuarios, registro con
 * encriptación BCrypt
 * y asignación del rol por defecto.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private static final String DEFAULT_ROLE = "ROLE_USER";

    @Override
    @Transactional
    public UserResponseDto registerUser(UserRegisterRequestDto registerRequestDto) {
        if (!registerRequestDto.getPassword().equals(registerRequestDto.getConfirmPassword())) {
            throw new BadRequestException("Las contraseñas no coinciden. Por favor verifique los datos.");
        }

        if (isEmailAlreadyRegistered(registerRequestDto.getEmail())) {
            throw new BadRequestException(
                    "El correo electrónico '" + registerRequestDto.getEmail() + "' ya se encuentra registrado.");
        }

        RoleEntity userRole = roleRepository.findByName(DEFAULT_ROLE)
                .orElseThrow(() -> new ResourceNotFoundException("Rol", "nombre", DEFAULT_ROLE));

        UserEntity userEntity = userMapper.toEntity(registerRequestDto, userRole);
        userEntity.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));

        UserEntity savedUser = userRepository.save(userEntity);
        return userMapper.toResponseDto(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserByEmail(String email) {
        UserEntity userEntity = getUserEntityByEmail(email);
        return userMapper.toResponseDto(userEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getUserEntityByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "email", email));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isEmailAlreadyRegistered(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponseDto)
                .toList();
    }
}