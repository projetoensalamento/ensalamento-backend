package br.univille.projetohotelpracachorro.dto;

import br.univille.projetohotelpracachorro.entity.ensalamento.Materia;
import lombok.Data;

@Data
public class MateriaResponse {
    private Long id;
    private String nome;
    private String sigla;

    public MateriaResponse(Materia materia) {
        this.id = materia.getId();
        this.nome = materia.getNome();
        this.sigla = materia.getSigla();
    }
}
