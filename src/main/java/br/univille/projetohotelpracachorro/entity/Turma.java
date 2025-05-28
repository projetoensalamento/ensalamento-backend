package br.univille.projetohotelpracachorro.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private int ano;

    private int semestre;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;
}
