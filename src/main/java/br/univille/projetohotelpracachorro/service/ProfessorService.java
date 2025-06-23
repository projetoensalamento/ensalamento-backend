package br.univille.projetohotelpracachorro.service;

import br.univille.projetohotelpracachorro.dto.request.ProfessorRequest;
import br.univille.projetohotelpracachorro.dto.response.ProfessorResponse;
import br.univille.projetohotelpracachorro.entity.Professor;
import br.univille.projetohotelpracachorro.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public Professor save(Professor professor) {
        return professorRepository.save(professor);
    }

    public Professor criaProfessor(ProfessorRequest professorRequest) {
        Professor professor = new Professor();
        professor.setNome(professorRequest.getNome());
        return professorRepository.save(professor);
    }

    public List<ProfessorResponse> findAll(){

        return professorRepository.findAll()
                .stream()
                .map(ProfessorResponse::new)
                .collect(Collectors.toList());
    }

    public Professor findById(Long id){
        return professorRepository.findById(id).get();
    }

    public void delete(Long id){
        professorRepository.deleteById(id);
    }

}
