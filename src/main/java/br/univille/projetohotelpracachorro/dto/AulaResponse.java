package br.univille.projetohotelpracachorro.dto;

import br.univille.projetohotelpracachorro.entity.Aula;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AulaResponse {
    private Long id;

    private Long turmaId;
    private String turmaNome;

    private Long professorId;
    private String professorNome;

    private Long materiaId;
    private String materiaNome;

    private String diaSemana;
    private Integer horario;

    public AulaResponse(Aula aula){
        this.id = aula.getId();
        this.turmaId = aula.getTurma().getId();
        this.turmaNome = aula.getTurma().getNome();
        this.professorId = aula.getProfessor().getId();
        this.professorNome = aula.getProfessor().getNome();
        this.materiaId = aula.getMateria().getId();
        this.materiaNome = aula.getMateria().getNome();
    }

}
