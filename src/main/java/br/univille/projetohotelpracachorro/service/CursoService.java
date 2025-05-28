package br.univille.projetohotelpracachorro.service;

import br.univille.projetohotelpracachorro.dto.ensalamento.CursoRequest;
import br.univille.projetohotelpracachorro.dto.ensalamento.CursoResponse;
import br.univille.projetohotelpracachorro.entity.ensalamento.Curso;
import br.univille.projetohotelpracachorro.repository.ensalamento.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .toList();
    }

    public Curso findById(Long id){
        return cursoRepository.findById(id).get();
    }

    public void delete(Long id){
        cursoRepository.deleteById(id);
    }
}
