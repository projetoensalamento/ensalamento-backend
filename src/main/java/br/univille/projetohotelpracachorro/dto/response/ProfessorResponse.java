package br.univille.projetohotelpracachorro.dto.response;

import br.univille.projetohotelpracachorro.entity.Professor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfessorResponse {
    private Long id;
    private String nome;

    public ProfessorResponse(Professor professor) {
        this.id = professor.getId();
        this.nome = professor.getNome();
    }

}
