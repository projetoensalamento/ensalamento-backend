package br.univille.projetohotelpracachorro.entity;

import lombok.Data;


@Data
@Entity
public class Aula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numero;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "turma_id")
    private Turma turma;

    @ManyToOne
    @JoinColumn(name = "materia_id")
    private Materia materia;

    @ManyToOne
    @JoinColumn(name = "diaSemana_id")
    private DiaSemana diaSemana;

    @ManyToOne
    @JoinColumn(name = "horario_id")
    private Horario horario;

}
