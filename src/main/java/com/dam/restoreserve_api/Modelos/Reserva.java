package com.dam.restoreserve_api.Modelos;

import java.time.LocalDateTime;

import com.dam.restoreserve_api.Enums.Estado;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reservas")
@Getter
@Setter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @Column(nullable = false)
    private int numPersonas;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Estado estado; // CONFIRMADA, CANCELADA, AUSENTE.

    @Column(length = 150)
    private String anotaciones;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_usuario", nullable = false) // Crea la columna para la FK.
    private Usuario usuario;

    @ManyToOne(optional = false) // requerimiento a nivel java.
    @JoinColumn(name = "id_mesa", nullable = false) // req. nivel BD.
    private Mesa mesa;

}
