package br.univille.projetohotelpracachorro.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProfessorRequest {
    @NotBlank
    @Size(min = 3, max = 70)
    private String nome;
}
