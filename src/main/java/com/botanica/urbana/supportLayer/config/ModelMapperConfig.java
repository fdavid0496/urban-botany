package com.botanica.urbana.supportLayer.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración para registrar el Bean de ModelMapper en Spring.
 * Facilita la conversión de objetos entre Entidades JPA y DTOs de forma
 * automatizada.
 */
@Configuration
public class ModelMapperConfig {

    /**
     * Registra el Bean de ModelMapper en el contenedor de Spring (IoC).
     * Se aplica una estrategia de mapeo estricta (STRICT) para evitar coincidencias
     * ambiguas o mapeos accidentales entre campos con nombres similares.
     *
     * @return Instancia única configurada de ModelMapper.
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true);

        return modelMapper;
    }
}