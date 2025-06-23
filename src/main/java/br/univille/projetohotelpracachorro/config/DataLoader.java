package br.univille.projetohotelpracachorro.config;

import br.univille.projetohotelpracachorro.entity.DiaSemana;
import br.univille.projetohotelpracachorro.entity.Horario;
import br.univille.projetohotelpracachorro.repository.DiaSemanaRepository;
import br.univille.projetohotelpracachorro.repository.HorarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final DiaSemanaRepository diaSemanaRepository;
    private final HorarioRepository horarioRepository;

    @Override
    public void run(String... args) throws Exception {
        if(diaSemanaRepository.count() == 0) {
            diaSemanaRepository.saveAll(List.of(
                    new DiaSemana(null, "Segunda-feira"),
                    new DiaSemana(null, "Terça-feira"),
                    new DiaSemana(null, "Quarta-feira"),
                    new DiaSemana(null, "Quinta-feira"),
                    new DiaSemana(null, "Sexta-feira"),
                    new DiaSemana(null, "Sábado-feira")
            ));
            System.out.println("Dias da semana pré-cadastrados");
        }

        if(horarioRepository.count() == 0) {
            Horario horario1 = new Horario();
            horario1.setNumero(1);
            horario1.setHoraInicio(LocalTime.of(19,0));
            horario1.setHoraFim(LocalTime.of(20,30));

            Horario horario2 = new Horario();
            horario2.setNumero(2);
            horario2.setHoraInicio(LocalTime.of(20,50));
            horario2.setHoraFim(LocalTime.of(22,30));

            horarioRepository.saveAll(List.of(horario1, horario2));
            System.out.println("Horários pré-cadastrados");
        }
    }
}
