package br.univille.projetohotelpracachorro.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AulaRequest {
    private Long turmaId;
    private Long professorId;
    private Long materiaId;
    private Long salaId;
    private Long diaSemanaId;
    private Long horarioId;
    private String diaSemana;  // Ex: "SEGUNDA"
    private Integer horario;
}
