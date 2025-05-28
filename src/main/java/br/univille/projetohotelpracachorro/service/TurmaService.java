package br.univille.projetohotelpracachorro.service;

import br.univille.projetohotelpracachorro.dto.ensalamento.TurmaRequest;
import br.univille.projetohotelpracachorro.dto.ensalamento.TurmaResponse;
import br.univille.projetohotelpracachorro.entity.ensalamento.Curso;
import br.univille.projetohotelpracachorro.entity.ensalamento.Turma;
import br.univille.projetohotelpracachorro.repository.ensalamento.CursoRepository;
import br.univille.projetohotelpracachorro.repository.ensalamento.TurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TurmaService {
    private final TurmaRepository turmaRepository;

    private final CursoRepository cursoRepository;

    public Turma save(Turma turma) {
        return turmaRepository.save(turma);
    }


    public List<TurmaResponse> listar() {
        return turmaRepository.findAll()
                .stream()
                .map(TurmaResponse::new)
                .toList();
    }

    public Turma criar(TurmaRequest turmaRequest) {
        Turma turma = new Turma();
        turma.setNome(turmaRequest.getNome());
        turma.setAno(turmaRequest.getAno());
        turma.setSemestre(turmaRequest.getSemestre());

        Curso curso = cursoRepository.findById(turmaRequest.getCursoId()).get();
        turma.setCurso(curso);
        return turmaRepository.save(turma);
    }

    public Turma findById(Long id) {
        return turmaRepository.findById(id).get();
    }

}
