package br.univille.projetohotelpracachorro.controller;

import br.univille.projetohotelpracachorro.dto.ensalamento.MateriaRequest;
import br.univille.projetohotelpracachorro.dto.ensalamento.MateriaResponse;
import br.univille.projetohotelpracachorro.entity.ensalamento.Materia;
import br.univille.projetohotelpracachorro.service.ensalamento.MateriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/materias")
public class MateriaController {
    private final MateriaService materiaService;

    @PostMapping
    public ResponseEntity<?> criaMateria(@RequestBody MateriaRequest materiaRequest) {
        Materia materiaCriada = materiaService.criaMateria(materiaRequest);
        MateriaResponse response = new MateriaResponse(materiaCriada);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MateriaResponse>> listarMaterias() {
        List<MateriaResponse> listaMaterias = materiaService.listaMaterias();
        return ResponseEntity.status(HttpStatus.OK).body(listaMaterias);
    }
}
