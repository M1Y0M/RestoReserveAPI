package com.dam.restoreserve_api.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dam.restoreserve_api.Dtos.ReservationRequestDTO;
import com.dam.restoreserve_api.Dtos.ReservationResponseDTO;
import com.dam.restoreserve_api.Enums.Estado;
import com.dam.restoreserve_api.Modelos.Reserva;
import com.dam.restoreserve_api.Modelos.Usuario;
import com.dam.restoreserve_api.Modelos.Mesa;
import com.dam.restoreserve_api.Repository.ReservaRepository;
import com.dam.restoreserve_api.Repository.UsuarioRepository;
import com.dam.restoreserve_api.Repository.MesaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepo;
    private final MesaRepository mesaRepo;
    private final UsuarioRepository usuarioRepo;

    public List<ReservationResponseDTO> listarTodas() {
        
        List<Reserva> reservas = reservaRepo.findAll();

        return reservas.stream().map(reserva -> new ReservationResponseDTO(

            reserva.getId(),
            reserva.getMesa().getId(),
            reserva.getUsuario().getId(),
            reserva.getFechaHora(),
            reserva.getEstado()

        )).collect(Collectors.toList());

    }

    public ReservationResponseDTO crearReserva(ReservationRequestDTO dto) {
        
        Mesa mesa = mesaRepo.findById(dto.mesaId())
            .orElseThrow(() -> new IllegalArgumentException("La mesa no existe."));

        Usuario usuario = usuarioRepo.findById(dto.usuarioId()) // Nuevo...
            .orElseThrow(() -> new IllegalArgumentException("El usuario no existe."));

        LocalDateTime limiteAnterior = dto.fechaHora().minusHours(2);
        LocalDateTime limitePosterior = dto.fechaHora().plusHours(2);

        boolean conflicto = reservaRepo.existsByMesaAndEstadoAndFechaHoraBetween(
                mesa, 
                Estado.COMPLETADA, 
                limiteAnterior, 
                limitePosterior
        );

        if (conflicto) {
            throw new IllegalArgumentException("La mesa está ocupada por una reserva anterior.");
        }

        if (dto.numPersonas() > mesa.getCapacidad()) {
            throw new IllegalArgumentException("La mesa no tiene capacidad para el nº de personas de esta reserva.");
        }

        /* 
        List <Reserva> reservas = listarTodas();

        int reservasCompletadas = 0;

        for (Reserva r : reservas) {

            if (r.getEstado().equals("COMPLETADA") && r.getUsuario().getId() == dto.usuarioId());
            reservasCompletadas++;

        }
       
        if (mesa.getIsVip() && reservasCompletadas > 3) {
            throw new IllegalArgumentException("No puede reservar una mesa vip pq no tiene por lo menos 3 reservas completadas.");
        }
        */

        List <Reserva> reservasUsuario = reservaRepo.findByUsuarioId(usuario.getId());
        
        int reservasCompletadas = 0; 

        for (Reserva r : reservasUsuario) {
            if (r.getEstado() == Estado.COMPLETADA) {
                reservasCompletadas++;
            }
        }

        if(reservasCompletadas < 3 && mesa.getIsVip()) {
            throw new IllegalArgumentException("No puede reservar una mesa vip pq no tiene por lo menos 3 reservas completadas.");
        }

        Reserva nuevaReserva = new Reserva();
        nuevaReserva.setUsuario(usuario); // Nuevo... 
        nuevaReserva.setFechaHora(dto.fechaHora());
        nuevaReserva.setNumPersonas(dto.numPersonas());
        nuevaReserva.setMesa(mesa);
        nuevaReserva.setEstado(Estado.COMPLETADA);

        Reserva reservaGuardada = reservaRepo.save(nuevaReserva);

        return new ReservationResponseDTO(

            reservaGuardada.getId(),
            reservaGuardada.getMesa().getId(),
            reservaGuardada.getUsuario().getId(),
            reservaGuardada.getFechaHora(),
            reservaGuardada.getEstado()

        );
         
    }

    public ReservationResponseDTO cancelarReserva(Long id) {
        Reserva reserva = reservaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        reserva.setEstado(Estado.CANCELADA);
        
        Reserva reservaModificada = reservaRepo.save(reserva);

        return new ReservationResponseDTO(

            reservaModificada.getId(),
            reservaModificada.getMesa().getId(),
            reservaModificada.getUsuario().getId(),
            reservaModificada.getFechaHora(),
            reservaModificada.getEstado()

        );

    }
}

