package br.univille.projetohotelpracachorro.controller;

import br.univille.projetohotelpracachorro.dto.request.ProfessorRequest;
import br.univille.projetohotelpracachorro.dto.request.TurmaRequest;
import br.univille.projetohotelpracachorro.dto.response.TurmaResponse;
import br.univille.projetohotelpracachorro.entity.Turma;
import br.univille.projetohotelpracachorro.service.TurmaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/turmas")
@RequiredArgsConstructor
public class TurmaController {

    private final TurmaService turmaService;

    @PostMapping()
    public ResponseEntity<TurmaResponse> criaTurma(@RequestBody TurmaRequest turmaRequest) {
        Turma turmaCriada = turmaService.criar(turmaRequest);
        TurmaResponse response = new TurmaResponse(turmaCriada);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ModelAndView listaTurmas() {
        List<TurmaResponse> listaTurmas = turmaService.listar();
        ModelAndView mv = new ModelAndView("turma/index");
        mv.addObject("listaTurmas", listaTurmas);
        mv.addObject("turmaRequest", new TurmaRequest());
        return mv;
    }
}
