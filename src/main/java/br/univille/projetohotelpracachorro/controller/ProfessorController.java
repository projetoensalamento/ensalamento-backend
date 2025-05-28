package br.univille.projetohotelpracachorro.controller;

import br.univille.projetohotelpracachorro.dto.ensalamento.ProfessorRequest;
import br.univille.projetohotelpracachorro.dto.ensalamento.ProfessorResponse;
import br.univille.projetohotelpracachorro.entity.ensalamento.Aula;
import br.univille.projetohotelpracachorro.entity.ensalamento.Professor;
import br.univille.projetohotelpracachorro.service.ensalamento.AulaService;
import br.univille.projetohotelpracachorro.service.ensalamento.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/professores")
public class ProfessorController {
    private final ProfessorService professorService;

    private final AulaService aulaService;

    @Autowired
    public ProfessorController(ProfessorService professorService, AulaService aulaService) {
        this.professorService = professorService;
        this.aulaService = aulaService;
    }

    @GetMapping
    public ModelAndView listaProfessores() {
        List<ProfessorResponse> listaProfessores = professorService.findAll();
        ModelAndView mv = new ModelAndView("ensalamento/index");
        mv.addObject("listaProfessores", listaProfessores);
        return mv;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponse> consultaProfessor(@PathVariable Long id) {
        Professor professor = professorService.findById(id);
        if(professor == null) {
            System.out.println("Erro ao consultar dados deste professor");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não econtrado com este Id!");
        }

        return ResponseEntity.ok(new ProfessorResponse(professor));
    }

    @PostMapping()
    public ResponseEntity<ProfessorResponse> criaProfessor(@RequestBody ProfessorRequest professorRequest) {
        Professor professorCriado = professorService.criaProfessor(professorRequest);
        ProfessorResponse response = new ProfessorResponse(professorCriado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorResponse> alteraProfessor(@RequestBody ProfessorRequest professorRequest, @PathVariable Long id) {
        Professor professor = professorService.findById(id);

        if (professor == null) {
            System.out.println("Erro ao alterar dados do professor");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não econtrado com este Id!");
        }

        professor.setNome(professorRequest.getNome());
        professorService.save(professor);

        return ResponseEntity.ok(new ProfessorResponse(professor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluiProfessor(@PathVariable Long id) {
        Professor professor = professorService.findById(id);
        if (professor == null) {
            System.out.println("Erro ao excluir professor");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não econtrado com este Id!");
        }

        professorService.delete(id);
        return ResponseEntity.ok("Professor excluido com sucessso");
    }

    @GetMapping("/{id}/aulas")
    public ResponseEntity<List<Aula>> listaAulas(@PathVariable Long id) {
        Professor professor = professorService.findById(id);
        if (professor == null) {
            System.out.println("Erro ao listar as aulas deste professor");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não econtrado com este Id!");
        }

        List<Aula> listaAulas = aulaService.consultaAulasPorProfessor(id);
        return ResponseEntity.ok().body(listaAulas);
    }





}
