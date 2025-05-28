package br.univille.projetohotelpracachorro.dto;

import br.univille.projetohotelpracachorro.entity.ensalamento.Turma;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TurmaResponse {
    private Long id;
    private String nome;
    private int ano;
    private int semestre;
    private Long cursoId;
    private String cursoSigla;

    public TurmaResponse(Turma turma) {
        this.id = turma.getId();
        this.nome = turma.getNome();
        this.ano = turma.getAno();
        this.semestre = turma.getSemestre();
        this.cursoId = turma.getCurso().getId();
        this.cursoSigla = turma.getCurso().getSigla();
    }

}
