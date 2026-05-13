package com.dam.restoreserve_api.Controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dam.restoreserve_api.Dtos.ReservationRequestDTO;
import com.dam.restoreserve_api.Modelos.Reserva;
import com.dam.restoreserve_api.Service.ReservaService;

import jakarta.validation.Valid; 

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @GetMapping
    public ResponseEntity<List<Reserva>> obtenerTodas() {
        return ResponseEntity.ok(reservaService.listarTodas());
    }

    @PostMapping
    public ResponseEntity<Reserva> guardarReserva(@Valid @RequestBody ReservationRequestDTO reservaDto) {
        return ResponseEntity.ok(reservaService.crearReserva(reservaDto));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Reserva> cancelarReserva(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.cancelarReserva(id));
    }
}