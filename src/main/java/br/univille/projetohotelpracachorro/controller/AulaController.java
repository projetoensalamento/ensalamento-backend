package br.univille.projetohotelpracachorro.controller;

import br.univille.projetohotelpracachorro.dto.request.AulaRequest;
import br.univille.projetohotelpracachorro.dto.response.AulaResponse;
import br.univille.projetohotelpracachorro.entity.Aula;
import br.univille.projetohotelpracachorro.service.AulaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/aulas")
public class AulaController {
    private final AulaService aulaService;

    @PostMapping
    public ResponseEntity<AulaResponse> criaAula(@RequestBody AulaRequest aulaRequest) {
        Aula aulaCriada = aulaService.criaAula(aulaRequest);
        AulaResponse response = new AulaResponse(aulaCriada);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/por-turma/{id}")
    public ResponseEntity<List<Aula>> listarPorTurmas(@PathVariable Long id) {
        return ResponseEntity.ok().body(aulaService.consultaAulasPorTurma(id));
    }

    @GetMapping("/por-professor/{id}")
    public ResponseEntity<List<Aula>> listarPorProfessores(@PathVariable Long id) {
        return ResponseEntity.ok().body(aulaService.consultaAulasPorProfessor(id));
    }

}
