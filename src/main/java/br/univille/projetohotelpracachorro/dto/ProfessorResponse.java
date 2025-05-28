package br.univille.projetohotelpracachorro.dto;

import br.univille.projetohotelpracachorro.entity.ensalamento.Professor;
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
