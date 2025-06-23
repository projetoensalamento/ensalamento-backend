package br.univille.projetohotelpracachorro.repository;

import br.univille.projetohotelpracachorro.entity.Materia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MateriaRepository extends JpaRepository<Materia, Long> {
    List<Materia> findAllByCurso_Id(Long cursoId);

}
