package br.univille.projetohotelpracachorro.service;

import br.univille.projetohotelpracachorro.dto.ensalamento.MateriaRequest;
import br.univille.projetohotelpracachorro.dto.ensalamento.MateriaResponse;
import br.univille.projetohotelpracachorro.entity.ensalamento.Curso;
import br.univille.projetohotelpracachorro.entity.ensalamento.Materia;
import br.univille.projetohotelpracachorro.repository.ensalamento.CursoRepository;
import br.univille.projetohotelpracachorro.repository.ensalamento.MateriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MateriaService {

    private final MateriaRepository materiaRepository;

    private final CursoRepository cursoRepository;


    public List<Materia> consultaMateriasPorId(Long id){
        return materiaRepository.findAllByCurso_Id(id);
    }

    public Materia save(Materia materia){
        return materiaRepository.save(materia);
    }

    public List<MateriaResponse> listaMaterias(){
        return materiaRepository.findAll()
                .stream()
                .map(MateriaResponse::new)
                .toList();
    }

    public Materia criaMateria(MateriaRequest materiaRequest){
        Materia materia = new Materia();
        materia.setNome(materiaRequest.getNome());
        materia.setSigla(materiaRequest.getSigla());

        Curso curso = cursoRepository.findById(materiaRequest.getCursoId()).get();
        materia.setCurso(curso);
        return materiaRepository.save(materia);
    }
}
