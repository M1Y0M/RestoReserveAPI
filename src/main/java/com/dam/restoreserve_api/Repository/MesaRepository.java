package com.dam.restoreserve_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dam.restoreserve_api.Modelos.Mesa;

@Repository
public interface MesaRepository extends JpaRepository <Mesa, Long> {

}
