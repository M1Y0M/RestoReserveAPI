package com.dam.restoreserve_api.Repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dam.restoreserve_api.Enums.Estado;
import com.dam.restoreserve_api.Modelos.Mesa;
import com.dam.restoreserve_api.Modelos.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository <Reserva, Long> {

boolean existsByMesaAndEstadoAndFechaHoraBetween(Mesa mesa, Estado estado, LocalDateTime inicio, LocalDateTime fin);
}
