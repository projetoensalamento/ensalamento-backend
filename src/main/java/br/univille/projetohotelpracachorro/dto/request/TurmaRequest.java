package br.univille.projetohotelpracachorro.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TurmaRequest {
    private String nome;
    private int ano;
    private int semestre;
    private Long cursoId;
}
