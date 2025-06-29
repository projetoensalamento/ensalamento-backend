package br.univille.projetohotelpracachorro.dto.response;

import br.univille.projetohotelpracachorro.entity.Curso;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CursoResponse {
    private Long id;
    private String nome;
    private String sigla;

    public CursoResponse(Curso curso) {
        this.id = curso.getId();
        this.nome = curso.getNome();
        this.sigla = curso.getSigla();
    }
}
