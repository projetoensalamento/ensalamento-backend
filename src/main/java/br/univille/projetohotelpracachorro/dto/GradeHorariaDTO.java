package br.univille.projetohotelpracachorro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeHorariaDTO {
    private DayOfWeek diaSemana;
    private AulaHorarioDTO horario1;
    private AulaHorarioDTO horario2;
}
