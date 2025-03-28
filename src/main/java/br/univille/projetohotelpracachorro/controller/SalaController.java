package br.univille.projetohotelpracachorro.controller;

import br.univille.projetohotelpracachorro.dto.SalaDTO;
import br.univille.projetohotelpracachorro.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ensalamento")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @GetMapping
    public ResponseEntity<List<SalaDTO>> getAllSalas() {
        List<SalaDTO> listaSalas = salaService.getAll();
        return ResponseEntity.ok(listaSalas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaDTO> getSalaById(@PathVariable("id") Long id) {
        var sala = salaService.findById(id);
        if (sala == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sala);
    }

    @PostMapping
    public ResponseEntity<SalaDTO> createSala(@RequestBody SalaDTO salaDTO) {
        var novaSala = salaService.save(salaDTO);
        return ResponseEntity.ok(novaSala);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaDTO> updateSala(@PathVariable("id") Long id, @RequestBody SalaDTO salaDTO) {
        var salaAtualizada = salaService.update(id, salaDTO);
        if (salaAtualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(salaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSala(@PathVariable("id") int id) {
        salaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}