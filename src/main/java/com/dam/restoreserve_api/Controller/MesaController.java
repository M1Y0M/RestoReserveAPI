package com.dam.restoreserve_api.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.dam.restoreserve_api.Modelos.Mesa;
import com.dam.restoreserve_api.Repository.MesaRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tables")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')") 
public class MesaController {

    private final MesaRepository mesaRepo;

    @GetMapping
    public ResponseEntity<List<Mesa>> listarMesas() {
        return ResponseEntity.ok(mesaRepo.findAll());
    }

    @PostMapping
    public ResponseEntity<Mesa> crearMesa(@RequestBody Mesa mesa) {
        return ResponseEntity.ok(mesaRepo.save(mesa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarMesa(@PathVariable Long id) {
        mesaRepo.deleteById(id);
        return ResponseEntity.ok("Mesa eliminada correctamente");
    }

    


}
