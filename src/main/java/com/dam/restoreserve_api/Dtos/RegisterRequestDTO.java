package com.dam.restoreserve_api.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(

    @NotBlank(message = "El nombre de usuario no puede estar vacío.")
    @Size(max = 20, message = "El nombre de usuario no puede contener más de 20 caracteres.")
    String username,

    @NotBlank(message = "La contraseña no puede estar vacía.")
    @Size(min = 8, max = 20, message = "La contraseña debe tener entre 8 y 20 caracteres.")
    String password

) {}
