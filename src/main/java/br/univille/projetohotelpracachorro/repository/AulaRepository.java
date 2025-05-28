package br.univille.projetohotelpracachorro.repository;

import br.univille.projetohotelpracachorro.entity.ensalamento.Aula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AulaRepository extends JpaRepository<Aula, Long> {
    List<Aula> findAllByProfessor_Id(Long professorId);

    List<Aula> findAllByTurma_Id(Long turmaId);

    Optional<Aula> findByDiaSemanaIdAndHorarioIdAndTurmaId(Long diaSemanaId, Long horarioId, Long turmaId);
}
