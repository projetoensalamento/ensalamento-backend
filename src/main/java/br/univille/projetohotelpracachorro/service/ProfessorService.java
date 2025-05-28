package br.univille.projetohotelpracachorro.service;

import br.univille.projetohotelpracachorro.dto.ensalamento.ProfessorRequest;
import br.univille.projetohotelpracachorro.dto.ensalamento.ProfessorResponse;
import br.univille.projetohotelpracachorro.entity.ensalamento.Professor;
import br.univille.projetohotelpracachorro.repository.ensalamento.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .toList();
    }

    public Professor findById(Long id){
        return professorRepository.findById(id).get();
    }

    public void delete(Long id){
        professorRepository.deleteById(id);
    }

}
