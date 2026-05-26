package com.dam.restoreserve_api.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.dam.restoreserve_api.Dtos.MesaDTO;
import com.dam.restoreserve_api.Modelos.Mesa;
import com.dam.restoreserve_api.Repository.MesaRepository;
import com.dam.restoreserve_api.Service.MesaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tables")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')") 
public class MesaController {

    private final MesaService mesaSer;

    @GetMapping
    public ResponseEntity<List<Mesa>> listarMesas() {
        return ResponseEntity.ok(mesaSer.obtenerMesas());
    }

    @PostMapping
    public ResponseEntity<Mesa> crearMesa(@RequestBody MesaDTO mesa) {
        return ResponseEntity.ok(mesaSer.crearMesa(mesa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarMesa(@PathVariable Long id) {
        mesaSer.borrarMesa(id);
        return ResponseEntity.ok("Mesa eliminada correctamente");
    }

    


}
