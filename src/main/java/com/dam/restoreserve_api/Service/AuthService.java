package com.dam.restoreserve_api.Service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dam.restoreserve_api.Config.JwtService;
import com.dam.restoreserve_api.Dtos.AuthResponse;
import com.dam.restoreserve_api.Dtos.LoginRequest;
import com.dam.restoreserve_api.Dtos.RegisterRequestDTO;
import com.dam.restoreserve_api.Enums.Rol;
import com.dam.restoreserve_api.Modelos.Usuario;
import com.dam.restoreserve_api.Repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class AuthService {

    private final UsuarioRepository usuarioRepo; 
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse registrar(RegisterRequestDTO request) {
        Usuario usuario = new Usuario();
        usuario.setUsername(request.username());
        usuario.setPasswordHash(passwordEncoder.encode(request.password()));
        usuario.setNombre(request.nombre());
        usuario.setRol(Rol.USER);

        usuarioRepo.save(usuario);

        String token = jwtService.generateToken(usuario.getUsername());
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        String token = jwtService.generateToken(request.username());
        return new AuthResponse(token);
    }
}