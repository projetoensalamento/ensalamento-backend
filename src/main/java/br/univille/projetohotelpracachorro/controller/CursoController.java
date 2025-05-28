package br.univille.projetohotelpracachorro.controller;

import br.univille.projetohotelpracachorro.dto.ensalamento.CursoRequest;
import br.univille.projetohotelpracachorro.dto.ensalamento.CursoResponse;
import br.univille.projetohotelpracachorro.entity.ensalamento.Curso;
import br.univille.projetohotelpracachorro.entity.ensalamento.Materia;
import br.univille.projetohotelpracachorro.service.ensalamento.CursoService;
import br.univille.projetohotelpracachorro.service.ensalamento.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Controller
@RequestMapping("/cursos")
public class CursoController {
    private final CursoService cursoService;

    private final MateriaService materiaService;

    @Autowired
    public CursoController(CursoService cursoService, MateriaService materiaService) {
        this.cursoService = cursoService;
        this.materiaService = materiaService;
    }

    @GetMapping
    public ResponseEntity<List<CursoResponse>> listaCursos() {
        List<CursoResponse> listaCursos = cursoService.findAll();
        return ResponseEntity.ok().body(listaCursos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponse> consultaCurso(@PathVariable Long id) {
        Curso curso = cursoService.findById(id);
        if(curso == null) {
            System.out.println("Erro ao consultar dados deste curso");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não econtrado com este Id!");
        }

        return ResponseEntity.ok(new CursoResponse(curso));
    }

    @PostMapping()
    public ResponseEntity<CursoResponse> criaCurso(@RequestBody CursoRequest cursoRequest) {
        Curso cursoCriado = cursoService.criaCurso(cursoRequest);
        CursoResponse response = new CursoResponse(cursoCriado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoResponse> alteraCurso(@RequestBody CursoRequest cursoRequest, @PathVariable Long id) {
        Curso curso = cursoService.findById(id);

        if (curso == null) {
            System.out.println("Erro ao alterar dados do curso");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não econtrado com este Id!");
        }

        curso.setNome(cursoRequest.getNome());
        curso.setSigla(cursoRequest.getSigla());
        cursoService.save(curso);

        return ResponseEntity.ok(new CursoResponse(curso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluiCurso(@PathVariable Long id) {
        Curso curso = cursoService.findById(id);
        if (curso == null) {
            System.out.println("Erro ao excluir curso");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não econtrado com este Id!");
        }

        cursoService.delete(id);
        return ResponseEntity.ok("Curso excluido com sucessso");
    }

    @GetMapping("/{id}/materias")
    public ResponseEntity<List<Materia>> listaMaterias(@PathVariable Long id) {
        Curso curso = cursoService.findById(id);
        if (curso == null) {
            System.out.println("Erro ao listar as materias deste curso");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não econtrado com este Id!");
        }

        List<Materia> listaMaterias = materiaService.consultaMateriasPorId(id);
        return ResponseEntity.ok().body(listaMaterias);
    }
}
