package br.univille.projetohotelpracachorro.dto;

import br.univille.projetohotelpracachorro.entity.ensalamento.Aula;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class AulaHorarioDTO {

    private String materia;
    private String professor;
    private String horario;
    private String sala;

    public AulaHorarioDTO(Aula aula) {
        this.materia = aula.getMateria().getSigla();
        this.professor = aula.getProfessor().getNome();

        if (aula.getHorario() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String inicio = aula.getHorario().getHoraInicio().format(formatter);
            String fim = aula.getHorario().getHoraFim().format(formatter);
            this.horario = inicio + " - " + fim;
        } else {
            this.horario = "-";
        }
    }

    public AulaHorarioDTO() {
        this.materia = "-";
        this.professor = "";
        this.sala = "";
    }
}
