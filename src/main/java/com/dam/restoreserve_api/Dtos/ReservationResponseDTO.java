package com.dam.restoreserve_api.Dtos;

import java.time.LocalDateTime;

import com.dam.restoreserve_api.Enums.Estado;

public record ReservationResponseDTO(

    Long id,
    Long mesaId,
    Long usuarioId,
    LocalDateTime fechaHora,
    Estado estado

) {}
