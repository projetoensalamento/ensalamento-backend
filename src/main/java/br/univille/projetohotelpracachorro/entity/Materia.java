package br.univille.projetohotelpracachorro.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String sigla;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;
}
