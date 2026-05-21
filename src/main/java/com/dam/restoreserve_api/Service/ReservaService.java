package com.dam.restoreserve_api.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.query.Jpa21Utils;
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
                Estado.COMPLETADA, 
                limiteAnterior, 
                horaSolicitada
        );

        if (conflicto) {
            throw new IllegalArgumentException("La mesa está ocupada por una reserva anterior.");
        }

        if (dto.numPersonas() > mesa.getCapacidad()) {
            throw new IllegalArgumentException("La mesa no tiene capacidad para el nº de personas de esta reserva.");
        }

        List <Reserva> reservas = listarTodas();

        int reservasCompletadas = 0; 

        for (Reserva r : reservas) {

            if (r.getEstado().equals("COMPLETADA") && r.getUsuario().getId() == dto.usuarioId());
            reservasCompletadas++;

        }
        
        if (mesa.getIsVip() && reservasCompletadas > 3) {
            throw new IllegalArgumentException("No puede reservar una mesa vip pq no tiene por lo menos 3 reservas completadas.");
        }

        Reserva nuevaReserva = new Reserva();
        nuevaReserva.setFechaHora(dto.fechaHora());
        nuevaReserva.setNumPersonas(dto.numPersonas());
        nuevaReserva.setMesa(mesa);
        nuevaReserva.setEstado(Estado.COMPLETADA);

        return reservaRepo.save(nuevaReserva);
    }

    public Reserva cancelarReserva(Long id) {
        Reserva reserva = reservaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        reserva.setEstado(Estado.CANCELADA);
        return reservaRepo.save(reserva);
    }
}