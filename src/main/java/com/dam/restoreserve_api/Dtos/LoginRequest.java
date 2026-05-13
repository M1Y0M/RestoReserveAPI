package com.dam.restoreserve_api.Dtos;

public record LoginRequest(
    String username,
    String password
) {}
