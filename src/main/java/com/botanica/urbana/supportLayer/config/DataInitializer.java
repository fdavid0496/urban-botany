package com.botanica.urbana.supportLayer.config;

import com.botanica.urbana.dataAccessLayer.repository.CategoryRepository;
import com.botanica.urbana.dataAccessLayer.repository.ProductRepository;
import com.botanica.urbana.dataAccessLayer.repository.RoleRepository;
import com.botanica.urbana.dataAccessLayer.repository.UserRepository;
import com.botanica.urbana.domainLayer.entity.CategoryEntity;
import com.botanica.urbana.domainLayer.entity.ProductEntity;
import com.botanica.urbana.domainLayer.entity.RoleEntity;
import com.botanica.urbana.domainLayer.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Componente que ejecuta la inicialización de datos maestros (roles, usuario ADMIN,
 * usuario USER por defecto, categorías y productos de muestra) al arrancar la aplicación.
 * Es idempotente (verifica la existencia antes de insertar para evitar duplicados).
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        initRoles();
        initCategories();
        initUsers();
        initSampleProducts();
    }

    private void initRoles() {
        if (!roleRepository.existsByName("ROLE_ADMIN")) {
            roleRepository.save(RoleEntity.builder().name("ROLE_ADMIN").build());
        }
        if (!roleRepository.existsByName("ROLE_USER")) {
            roleRepository.save(RoleEntity.builder().name("ROLE_USER").build());
        }
    }

    private void initCategories() {
        createCategoryIfNotFound("Plantas de Interior", "Hermosas plantas ornamentales para ambientar espacios cerrados.");
        createCategoryIfNotFound("Macetas y Jardineras", "Macetas de cerámica, barro y diseños modernos para tus plantas.");
        createCategoryIfNotFound("Herramientas de Jardín", "Palas, tijeras de podar y regaderas para el cuidado botánico.");
        createCategoryIfNotFound("Sustratos y Abonos", "Tierra abonada, fertilizantes orgánicos y sustratos especiales.");
    }

    private void createCategoryIfNotFound(String name, String description) {
        if (!categoryRepository.existsByName(name)) {
            categoryRepository.save(CategoryEntity.builder()
                    .name(name)
                    .description(description)
                    .build());
        }
    }

    private void initUsers() {
        RoleEntity adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow();
        RoleEntity userRole = roleRepository.findByName("ROLE_USER").orElseThrow();

        // Usuario Administrador Inicial (Credenciales: admin@botanica.com / admin123)
        if (!userRepository.existsByEmail("admin@botanica.com")) {
            userRepository.save(UserEntity.builder()
                    .fullName("Administrador Principal")
                    .email("admin@botanica.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(adminRole)
                    .build());
        }

        // Usuario Cliente Ejemplo (Credenciales: user@botanica.com / user123)
        if (!userRepository.existsByEmail("user@botanica.com")) {
            userRepository.save(UserEntity.builder()
                    .fullName("Cliente Ejemplo")
                    .email("user@botanica.com")
                    .password(passwordEncoder.encode("user123"))
                    .role(userRole)
                    .build());
        }
    }

    private void initSampleProducts() {
        if (productRepository.count() == 0) {
            CategoryEntity plantas = categoryRepository.findByName("Plantas de Interior").orElse(null);
            CategoryEntity macetas = categoryRepository.findByName("Macetas y Jardineras").orElse(null);

            if (plantas != null) {
                productRepository.saveAll(List.of(
                        ProductEntity.builder()
                                .name("Monstera Deliciosa")
                                .description("Planta tropical de hojas grandes con hendiduras características. Muy resistente.")
                                .price(new BigDecimal("45000.00"))
                                .imageUrl("/images/products/monstera.jpg")
                                .category(plantas)
                                .build(),
                        ProductEntity.builder()
                                .name("Sansevieria (Lengua de Suegra)")
                                .description("Planta purificadora de aire, ideal para principiantes por sus mínimos cuidados.")
                                .price(new BigDecimal("32000.00"))
                                .imageUrl("/images/products/sansevieria.jpg")
                                .category(plantas)
                                .build(),
                        ProductEntity.builder()
                                .name("Ficus Elástica")
                                .description("Arbolito de interior con hojas frondosas de verde oscuro brillante.")
                                .price(new BigDecimal("55000.00"))
                                .imageUrl("/images/products/ficus.jpg")
                                .category(plantas)
                                .build()
                ));
            }

            if (macetas != null) {
                productRepository.save(ProductEntity.builder()
                        .name("Maceta de Cerámica Artesanal")
                        .description("Maceta hecha a mano con acabado mate de color terracota.")
                        .price(new BigDecimal("28000.00"))
                        .imageUrl("/images/products/maceta-ceramica.jpg")
                        .category(macetas)
                        .build());
            }
        }
    }
}