package com.dam.restoreserve_api.Dtos;

import java.time.LocalDateTime;

import com.dam.restoreserve_api.Enums.Zona;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReservationRequestDTO(

    @NotNull(message = "Debe indicarse el usuario")
    Long usuarioId,

    @NotNull(message = "Debe indicarse la mesa.")
    Long mesaId,

    @NotNull(message = "Debe indicarse una zona.")
    Zona zona,

    @NotNull(message = "La fecha y hora son obligatorias.")
    @Future(message = "La reserva debe ser en una fecha futura.")
    LocalDateTime fechaHora,

    @Min(value = 1, message = "Mínimo 1 comensal")
    @Max(value = 12, message = "Máximo 12 comensajes por mesa")
    int numPersonas

) {}
