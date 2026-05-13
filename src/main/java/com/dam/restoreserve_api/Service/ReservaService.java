package com.dam.restoreserve_api.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dam.restoreserve_api.Dtos.ReservationRequestDTO;
import com.dam.restoreserve_api.Enums.Estado;
import com.dam.restoreserve_api.Modelos.Reserva;
import com.dam.restoreserve_api.Modelos.Mesa;
import com.dam.restoreserve_api.Repository.ReservaRepository;
import com.dam.restoreserve_api.Repository.MesaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepo;
    private final MesaRepository mesaRepo;

    public List<Reserva> listarTodas() {
        return reservaRepo.findAll();
    }

    public Reserva crearReserva(ReservationRequestDTO dto) {
        
        Mesa mesa = mesaRepo.findById(dto.mesaId())
                .orElseThrow(() -> new IllegalArgumentException("La mesa no existe."));

        LocalDateTime limiteAnterior = dto.fechaHora().minusHours(2);
        LocalDateTime horaSolicitada = dto.fechaHora();

        boolean conflicto = reservaRepo.existsByMesaAndEstadoAndFechaHoraBetween(
                mesa, 
                Estado.CONFIRMADA, 
                limiteAnterior, 
                horaSolicitada
        );

        if (conflicto) {
            throw new IllegalArgumentException("La mesa está ocupada por una reserva anterior.");
        }

        Reserva nuevaReserva = new Reserva();
        nuevaReserva.setFechaHora(dto.fechaHora());
        nuevaReserva.setNumPersonas(dto.numPersonas());
        nuevaReserva.setMesa(mesa);
        nuevaReserva.setEstado(Estado.CONFIRMADA);

        return reservaRepo.save(nuevaReserva);
    }

    public Reserva cancelarReserva(Long id) {
        Reserva reserva = reservaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        reserva.setEstado(Estado.CANCELADA);
        return reservaRepo.save(reserva);
    }
}