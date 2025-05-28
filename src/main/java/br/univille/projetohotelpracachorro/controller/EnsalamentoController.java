package br.univille.projetohotelpracachorro.controller;

import br.univille.projetohotelpracachorro.dto.ensalamento.AulaHorarioDTO;
import br.univille.projetohotelpracachorro.dto.ensalamento.GradeHorariaDTO;
import br.univille.projetohotelpracachorro.entity.ensalamento.Aula;
import br.univille.projetohotelpracachorro.entity.ensalamento.Turma;
import br.univille.projetohotelpracachorro.service.ensalamento.AulaService;
import br.univille.projetohotelpracachorro.service.ensalamento.TurmaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ensalamento")
public class EnsalamentoController {
    private final TurmaService turmaService;
    private final AulaService aulaService;

    @GetMapping("/turma/{id}")
    public String visualizarEnsalamentoTurma(@PathVariable Long id, Model model) {
        Turma turma = turmaService.findById(id);
        if(turma == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada!");
        }

        List<Aula> aulas = aulaService.consultaAulasPorTurma(id);
        aulas.sort(Comparator.comparing(a -> a.getHorario().getId()));
        // Monta o DTO por dia da semana
        List<GradeHorariaDTO> grade = new ArrayList<>();
        for (DayOfWeek dia : DayOfWeek.values()) {
            System.out.println("DIA=" + dia);
            if(dia.name().equals("SUNDAY")) continue;

            AulaHorarioDTO horario1 = new AulaHorarioDTO();
            AulaHorarioDTO horario2 = new AulaHorarioDTO();

            for (Aula aula : aulas) {
                DayOfWeek diaAula = mapaDias.getOrDefault(
                        aula.getDiaSemana().getNome().toUpperCase(), null
                );
                if (diaAula != null && diaAula == dia) {
                    if (aula.getHorario().getNumero() == 1) {
                        horario1 = new AulaHorarioDTO(aula);
                    } else if (aula.getHorario().getNumero() == 2) {
                        horario2 = new AulaHorarioDTO(aula);
                    }
                }
            }

            GradeHorariaDTO linha = new GradeHorariaDTO();
            linha.setDiaSemana(dia);
            linha.setHorario1(horario1);
            linha.setHorario2(horario2);
            grade.add(linha);
        }

        model.addAttribute("turma", turma);
        model.addAttribute("gradeHoraria", grade);
        return "turma/ensalamento";
    }

    Map<String, DayOfWeek> mapaDias = Map.of(
            "SEGUNDA", DayOfWeek.MONDAY,
            "TERCA-FEIRA", DayOfWeek.TUESDAY,
            "QUARTA-FEIRA", DayOfWeek.WEDNESDAY,
            "QUINTA-FEIRA", DayOfWeek.THURSDAY,
            "SEXTA-FEIRA", DayOfWeek.FRIDAY,
            "SABADO", DayOfWeek.SATURDAY,
            "DOMINGO", DayOfWeek.SUNDAY
    );
}
