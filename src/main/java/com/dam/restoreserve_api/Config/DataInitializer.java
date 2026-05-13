package com.dam.restoreserve_api.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dam.restoreserve_api.Modelos.Usuario;
import com.dam.restoreserve_api.Enums.Rol;
import com.dam.restoreserve_api.Repository.UsuarioRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepo, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!usuarioRepo.existsByUsername("admin")) {
                Usuario admin = Usuario.builder()
                        .username("admin")
                        .passwordHash(passwordEncoder.encode("admin123"))
                        .rol(Rol.ADMIN)
                        .nombre("Admin")
                        .apellidos("Sistema")
                        .telefono("600000000")
                        .email("admin@restoreserve.com")
                        .build();

                usuarioRepo.save(admin);
            }
        };
    }
}