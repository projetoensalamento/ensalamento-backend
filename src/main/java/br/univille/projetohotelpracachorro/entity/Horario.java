package br.univille.projetohotelpracachorro.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;
@Data
@Entity
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numero;

    private LocalTime horaInicio;
    private LocalTime horaFim;

}
