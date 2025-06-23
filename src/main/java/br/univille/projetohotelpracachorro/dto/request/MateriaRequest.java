package br.univille.projetohotelpracachorro.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MateriaRequest {
    private String nome;
    private String sigla;
    private Long cursoId;
}
