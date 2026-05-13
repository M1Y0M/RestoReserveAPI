package com.dam.restoreserve_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dam.restoreserve_api.Modelos.Reserva;

public interface ReservaRepository extends JpaRepository <Reserva, Long> {

}
