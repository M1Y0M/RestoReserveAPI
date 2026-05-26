package com.dam.restoreserve_api.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dam.restoreserve_api.Dtos.MesaDTO;
import com.dam.restoreserve_api.Enums.Zona;
import com.dam.restoreserve_api.Modelos.Mesa;
import com.dam.restoreserve_api.Repository.MesaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MesaService {

    private final MesaRepository mesaRepo;

    public Mesa crearMesa(MesaDTO mesa) {

        Mesa nuevaMesa = new Mesa();
        nuevaMesa.setCapacidad(mesa.capacidad());
        nuevaMesa.setIsVip(mesa.isVip());
        nuevaMesa.setZona(Zona.INTERIOR); // Lo pongo automático pq como no voy a hacer nada con esto de momento.
        
        return mesaRepo.save(nuevaMesa);

    }

    public List<Mesa> obtenerMesas() {
        return mesaRepo.findAll();
    }

    public void borrarMesa(Long id) {
        Mesa mesa = mesaRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));

        mesaRepo.delete(mesa);

    }

}
