package com.dam.restoreserve_api.Dtos;

import java.time.LocalDateTime;

public record ReservationResponseDTO(

    Long id,
    String mesaNombre,
    String clienteNombre,
    LocalDateTime fechaHora,
    String estado

) {}
