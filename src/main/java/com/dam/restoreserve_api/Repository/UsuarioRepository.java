package com.dam.restoreserve_api.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dam.restoreserve_api.Modelos.Usuario;

public interface UsuarioRepository extends JpaRepository <Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

}
