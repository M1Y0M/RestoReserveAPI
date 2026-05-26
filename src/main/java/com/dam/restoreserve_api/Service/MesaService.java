package com.dam.restoreserve_api.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public MesaDTO crearMesa(MesaDTO mesa) {

        Mesa nuevaMesa = new Mesa();
        nuevaMesa.setCapacidad(mesa.capacidad());
        nuevaMesa.setIsVip(mesa.isVip());
        nuevaMesa.setZona(Zona.INTERIOR); // Lo pongo automático pq como no voy a hacer nada con esto de momento.
        
        Mesa mesaGuardada = mesaRepo.save(nuevaMesa);

        return new MesaDTO(
            mesaGuardada.getId(),
            mesaGuardada.getCapacidad(),
            mesaGuardada.getIsVip()
        );

    }

    public List<MesaDTO> obtenerMesas() {

        List<Mesa> mesas = mesaRepo.findAll();

        return mesas.stream().map(mesa -> new MesaDTO(

            mesa.getId(),
            mesa.getCapacidad(),
            mesa.getIsVip()

        )).collect(Collectors.toList());

    }

    public void borrarMesa(Long id) {
        Mesa mesa = mesaRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));

        mesaRepo.delete(mesa);

    }

}
