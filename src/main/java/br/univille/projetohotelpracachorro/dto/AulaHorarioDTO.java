package br.univille.projetohotelpracachorro.dto;

import br.univille.projetohotelpracachorro.entity.Aula;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class AulaHorarioDTO {

    private Long aulaId;
    private String materia;
    private String professor;
    private String horarioExibicao;
    private String sala;
    private boolean temAula;

    public AulaHorarioDTO(Aula aula) {
        if(aula != null) {
            this.aulaId = aula.getId();
            this.materia = aula.getMateria().getSigla();
            this.professor = aula.getProfessor().getNome();

            if (aula.getHorario() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                String inicio = aula.getHorario().getHoraInicio().format(formatter);
                String fim = aula.getHorario().getHoraFim().format(formatter);
                this.horarioExibicao = inicio + " - " + fim;
            } else {
                this.horarioExibicao = "-";
            }
            this.sala = aula.getSala();
            this.temAula = true;
        }else {
            // Se a aula passada for nula, inicializa como um slot vazio
            initEmptySlot();
        }
    }

    public AulaHorarioDTO() {
        initEmptySlot(); // Chama um método auxiliar para inicializar como slot vazio
    }

    // Método auxiliar para inicializar o DTO como um slot vazio
    private void initEmptySlot() {
        this.aulaId = null;
        this.materia = "Vazio"; // Ou "-" para indicar que não há aula
        this.professor = "";    // Professor vazio para slot sem aula
        this.horarioExibicao = ""; // Horário não se aplica diretamente a um slot vazio, pode ser deixado em branco
        this.sala = ""; // Sala vazia para slot sem aula
        this.temAula = false;   // Indica que este DTO representa um slot vazio
    }
}
