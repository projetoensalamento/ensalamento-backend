package br.univille.projetohotelpracachorro.service;

import br.univille.projetohotelpracachorro.dto.request.TurmaRequest;
import br.univille.projetohotelpracachorro.dto.response.TurmaResponse;
import br.univille.projetohotelpracachorro.entity.Curso;
import br.univille.projetohotelpracachorro.entity.Turma;
import br.univille.projetohotelpracachorro.repository.CursoRepository;
import br.univille.projetohotelpracachorro.repository.TurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
                .collect(Collectors.toList());
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
