package com.dam.restoreserve_api.Controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.dam.restoreserve_api.Dtos.ReservationRequestDTO;
import com.dam.restoreserve_api.Dtos.ReservationResponseDTO;
import com.dam.restoreserve_api.Service.ReservaService;

import jakarta.validation.Valid; 

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReservationResponseDTO>> obtenerTodas() {  
        return ResponseEntity.ok(reservaService.listarTodas());
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDTO> guardarReserva(@Valid @RequestBody ReservationRequestDTO reservaDto) {
        return ResponseEntity.ok(reservaService.crearReserva(reservaDto));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<ReservationResponseDTO> cancelarReserva(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.cancelarReserva(id));
    }
}