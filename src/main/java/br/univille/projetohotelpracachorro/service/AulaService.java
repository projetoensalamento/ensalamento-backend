package br.univille.projetohotelpracachorro.service;

import br.univille.projetohotelpracachorro.dto.request.AulaRequest;
import br.univille.projetohotelpracachorro.dto.response.AulaResponse;
import br.univille.projetohotelpracachorro.entity.*;
import br.univille.projetohotelpracachorro.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AulaService {
    private final AulaRepository aulaRepository;
    private final ProfessorRepository professorRepository;
    private final TurmaRepository turmaRepository;
    private final MateriaRepository materiaRepository;
    private final DiaSemanaRepository diaSemanaRepository;
    private final HorarioRepository horarioRepository;

    public Aula criaAula(AulaRequest aulaRequest) {
        Optional<Aula> existente = aulaRepository
                .findByDiaSemanaIdAndHorarioIdAndTurmaId(
                        aulaRequest.getDiaSemanaId(),
                        aulaRequest.getHorarioId(),
                        aulaRequest.getTurmaId());

        if (existente.isPresent()) {
            throw new RuntimeException("Já existe uma aula com esse dia, horário e turma.");
        }

        Aula aula = new Aula();
        mapAulaRequestToAula(aulaRequest, aula);
        return aulaRepository.save(aula);
    }

    public Aula atualizaAula(Long id, AulaRequest aulaRequest) {
        Aula aulaExistente = aulaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aula não encontrada com o ID: " + id));

        mapAulaRequestToAula(aulaRequest, aulaExistente); // Atualiza os campos editáveis
        return aulaRepository.save(aulaExistente);
    }

    public Aula findById(Long id) {
        return aulaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aula nao encontrada com este id"));
    }




    public List<AulaResponse> findAll(){
        return aulaRepository.findAll()
                .stream()
                .map(AulaResponse::new)
                .collect(Collectors.toList());
    }

    public List<Aula> consultaAulasPorProfessor(Long id){
        return aulaRepository.findAllByProfessor_Id(id);
    }

    public List<Aula> consultaAulasPorTurma(Long id){
        return aulaRepository.findAllByTurma_Id(id);
    }

    // Método auxiliar para mapear AulaRequest para Aula (reutilizável para criar e atualizar)
    private void mapAulaRequestToAula(AulaRequest aulaRequest, Aula aula) {
        DiaSemana diaSemana = diaSemanaRepository.findById(aulaRequest.getDiaSemanaId())
                .orElseThrow(() -> new IllegalArgumentException("Erro procurando dia da semana"));
        aula.setDiaSemana(diaSemana);

        Horario horario = horarioRepository.findById(aulaRequest.getHorarioId())
                .orElseThrow(() -> new IllegalArgumentException("Erro procurando horário"));
        aula.setHorario(horario);

        Materia materia = materiaRepository.findById(aulaRequest.getMateriaId())
                .orElseThrow(() -> new IllegalArgumentException("Erro procurando matéria"));
        aula.setMateria(materia);

        Professor professor = professorRepository.findById(aulaRequest.getProfessorId())
                .orElseThrow(() -> new IllegalArgumentException("Erro procurando professor"));
        aula.setProfessor(professor);

        Turma turma = turmaRepository.findById(aulaRequest.getTurmaId())
                .orElseThrow(() -> new IllegalArgumentException("Erro procurando turma"));
        aula.setTurma(turma);

        aula.setSala(aulaRequest.getSala()); // <-- Adiciona a sala
    }

}
