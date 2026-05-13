package com.dam.restoreserve_api.Dtos;

import java.time.LocalDateTime;

public record ErrorResponse(
    String code,
    String message,
    LocalDateTime timestamp
) {}