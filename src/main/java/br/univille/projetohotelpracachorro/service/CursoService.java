package br.univille.projetohotelpracachorro.service;

import br.univille.projetohotelpracachorro.dto.request.CursoRequest;
import br.univille.projetohotelpracachorro.dto.response.CursoResponse;
import br.univille.projetohotelpracachorro.entity.Curso;
import br.univille.projetohotelpracachorro.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoService {
    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public Curso save(Curso curso) {
        return cursoRepository.save(curso);
    }

    public Curso criaCurso(CursoRequest cursoRequest) {
        Curso curso = new Curso();
        curso.setNome(cursoRequest.getNome());
        curso.setSigla(cursoRequest.getSigla());
        return cursoRepository.save(curso);
    }

    public List<CursoResponse> findAll(){

        return cursoRepository.findAll()
                .stream()
                .map(CursoResponse::new)
                .collect(Collectors.toList());
    }

    public Curso findById(Long id){
        return cursoRepository.findById(id).get();
    }

    public void delete(Long id){
        cursoRepository.deleteById(id);
    }
}
