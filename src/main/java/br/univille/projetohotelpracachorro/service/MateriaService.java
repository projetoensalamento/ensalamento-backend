package br.univille.projetohotelpracachorro.service;

import br.univille.projetohotelpracachorro.dto.request.MateriaRequest;
import br.univille.projetohotelpracachorro.dto.response.MateriaResponse;
import br.univille.projetohotelpracachorro.entity.Curso;
import br.univille.projetohotelpracachorro.entity.Materia;
import br.univille.projetohotelpracachorro.repository.CursoRepository;
import br.univille.projetohotelpracachorro.repository.MateriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
                .collect(Collectors.toList());
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
