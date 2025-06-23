package br.univille.projetohotelpracachorro.controller;

import br.univille.projetohotelpracachorro.dto.AulaHorarioDTO;
import br.univille.projetohotelpracachorro.dto.GradeHorariaDTO;
import br.univille.projetohotelpracachorro.dto.request.AulaRequest;
import br.univille.projetohotelpracachorro.entity.Aula;
import br.univille.projetohotelpracachorro.entity.DiaSemana;
import br.univille.projetohotelpracachorro.entity.Horario;
import br.univille.projetohotelpracachorro.entity.Turma;
import br.univille.projetohotelpracachorro.repository.*;
import br.univille.projetohotelpracachorro.service.AulaService;
import br.univille.projetohotelpracachorro.service.TurmaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.DayOfWeek;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ensalamento")
public class EnsalamentoController {
    private final TurmaService turmaService;
    private final AulaService aulaService;
    private final DiaSemanaRepository diaSemanaRepository; // Para buscar os dias da semana
    private final HorarioRepository horarioRepository; // Para buscar os horários fixos
    private final ProfessorRepository professorRepository;
    private final MateriaRepository materiaRepository;
    private final AulaRepository aulaRepository;

    // Mapa para converter nome do dia da semana (DB) para DayOfWeek
    private final Map<String, DayOfWeek> mapaDias = new LinkedHashMap<>();
    {
        mapaDias.put("SEGUNDA-FEIRA", DayOfWeek.MONDAY);
        mapaDias.put("TERÇA-FEIRA", DayOfWeek.TUESDAY);
        mapaDias.put("QUARTA-FEIRA", DayOfWeek.WEDNESDAY);
        mapaDias.put("QUINTA-FEIRA", DayOfWeek.THURSDAY);
        mapaDias.put("SEXTA-FEIRA", DayOfWeek.FRIDAY);
        mapaDias.put("SÁBADO", DayOfWeek.SATURDAY);
        mapaDias.put("DOMINGO", DayOfWeek.SUNDAY);
    }

    @GetMapping("/turma/{id}") // Mudança na URL para ser mais claro
    public String visualizarEnsalamentoTurma(@PathVariable Long id, Model model) {
        Turma turma = turmaService.findById(id);
        if (turma == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada!");
        }

        // 1. Obter todos os dias da semana do banco de dados (para garantir ordem e nomes)
        List<DiaSemana> diasSemanaDB = diaSemanaRepository.findAll(
                // Ordenar por ID para garantir uma ordem consistente, se seus IDs forem sequenciais
                Sort.by("id")
        );

        // 2. Obter todos os horários fixos do banco de dados
        List<Horario> horariosFixos = horarioRepository.findAll(
                // Ordenar por 'numero' ou 'horaInicio' para garantir a ordem correta no display
                Sort.by("numero")
        );
        model.addAttribute("horariosFixos", horariosFixos); // Passa para o Thymeleaf

        List<Aula> aulas = aulaService.consultaAulasPorTurma(id);

        aulas.sort(Comparator.comparing((Aula aula) -> aula.getDiaSemana().getId()) // Ordena primeiro por dia
                .thenComparing(aula -> aula.getHorario().getNumero())); // Depois por número do horário

        List<GradeHorariaDTO> grade = new ArrayList<>();

        // Iterar pelos dias da semana obtidos do banco de dados
        for (DiaSemana diaDB : diasSemanaDB) {
            // Se você quiser excluir o domingo, faça aqui
            if (diaDB.getNome().equalsIgnoreCase("Domingo")) {
                continue;
            }

            GradeHorariaDTO linha = new GradeHorariaDTO();
            linha.setDiaSemana(mapaDias.getOrDefault(diaDB.getNome().toUpperCase(), null)); // Converte para DayOfWeek
            linha.setNomeDiaSemana(diaDB.getNome()); // Adiciona o nome do dia em português para o DTO

            // Inicializa os slots de horário para cada dia
            Map<Integer, AulaHorarioDTO> slotsHorario = new LinkedHashMap<>();
            for (Horario h : horariosFixos) {
                slotsHorario.put(h.getNumero(), new AulaHorarioDTO()); // AulaHorarioDTO vazio por padrão
            }

            // Preenche os slots com as aulas existentes
            for (Aula aula : aulas) {
                if (aula.getDiaSemana().getId().equals(diaDB.getId())) {
                    slotsHorario.put(aula.getHorario().getNumero(), new AulaHorarioDTO(aula));
                }
            }

            // Atribui os horários aos slots do DTO, garantindo que mesmo se não houver aula, o slot exista
            if (slotsHorario.containsKey(1)) {
                linha.setHorario1(slotsHorario.get(1));
            }
            if (slotsHorario.containsKey(2)) {
                linha.setHorario2(slotsHorario.get(2));
            }
            // Adicione mais slots se você tiver mais que 2 horários fixos

            grade.add(linha);
        }

        model.addAttribute("turma", turma);
        model.addAttribute("gradeHoraria", grade);
        return "turma/ensalamento";
    }

    @GetMapping("/editarAula/{aulaId}")
    public String exibirFormularioEdicaoAula(@PathVariable Long aulaId, Model model) {
        Aula aula = aulaService.findById(aulaId); // Busca a aula pelo ID
        if (aula == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aula não encontrada!");
        }

        // Popula o AulaRequest com os dados da aula existente
        AulaRequest aulaRequest = new AulaRequest();
        aulaRequest.setProfessorId(aula.getProfessor().getId());
        aulaRequest.setTurmaId(aula.getTurma().getId());
        aulaRequest.setMateriaId(aula.getMateria().getId());
        aulaRequest.setDiaSemanaId(aula.getDiaSemana().getId());
        aulaRequest.setHorarioId(aula.getHorario().getId());
        aulaRequest.setSala(aula.getSala());

        model.addAttribute("aula", aula); // Passa a entidade aula original para exibir dados fixos
        model.addAttribute("aulaRequest", aulaRequest); // Passa o DTO para o formulário
        model.addAttribute("professores", professorRepository.findAll());
        model.addAttribute("materias", materiaRepository.findAll());
        // Dia e Horário não são editáveis aqui, então não é necessário passar listas deles.

        return "aula/editarAula"; // Retorna para um novo template de edição de aula
    }


    // Novo método para exibir o formulário de CRIAÇÃO de uma aula em um slot vazio
    @GetMapping("/criarAula/{turmaId}/{diaSemanaId}/{horarioId}")
    public String exibirFormularioCriacaoAula(
            @PathVariable Long turmaId,
            @PathVariable Long diaSemanaId,
            @PathVariable Long horarioId,
            Model model) {

        Turma turma = turmaService.findById(turmaId);
        DiaSemana diaSemana = diaSemanaRepository.findById(diaSemanaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dia da semana não encontrado!"));
        Horario horario = horarioRepository.findById(horarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Horário não encontrado!"));

        // Cria um AulaRequest vazio para o formulário
        AulaRequest aulaRequest = new AulaRequest();
        aulaRequest.setTurmaId(turmaId);
        aulaRequest.setDiaSemanaId(diaSemanaId);
        aulaRequest.setHorarioId(horarioId);

        model.addAttribute("aulaRequest", aulaRequest);
        model.addAttribute("turma", turma);
        model.addAttribute("diaSemana", diaSemana);
        model.addAttribute("horario", horario);
        model.addAttribute("professores", professorRepository.findAll());
        model.addAttribute("materias", materiaRepository.findAll());

        return "aula/criarAula"; // Novo template para criação de aula
    }


    // Novo método para processar o POST do formulário de CRIAÇÃO de aula
    @PostMapping("/criarAula")
    public String criarAula(@ModelAttribute AulaRequest aulaRequest,
                            RedirectAttributes redirectAttributes) {
        try {
            aulaService.criaAula(aulaRequest);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Aula criada com sucesso!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
            // Se falhar, redireciona de volta para o formulário com os mesmos parâmetros
            return "redirect:/ensalamento/criarAula/" + aulaRequest.getTurmaId() + "/" +
                    aulaRequest.getDiaSemanaId() + "/" + aulaRequest.getHorarioId();
        }
        return "redirect:/ensalamento/turma/" + aulaRequest.getTurmaId(); // Redireciona de volta para o ensalamento
    }


    // Novo método para processar o PUT (ou POST) do formulário de edição de aula
    // Usamos @PostMapping aqui e configuramos o formulário Thymeleaf para enviar _method=PUT
    @PostMapping("/salvarEdicaoAula/{aulaId}")
    public String salvarEdicaoAula(@PathVariable Long aulaId,
                                   @ModelAttribute AulaRequest aulaRequest,
                                   RedirectAttributes redirectAttributes) {
        try {
            aulaService.atualizaAula(aulaId, aulaRequest);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Aula atualizada com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
            return "redirect:/ensalamento/editarAula/" + aulaId; // Redireciona para o formulário de edição com erro
        }
        // Redireciona de volta para o ensalamento da turma após a edição
        return "redirect:/ensalamento/turma/" + aulaRequest.getTurmaId();
    }

    // Se desejar um endpoint para exclusão
    @PostMapping("/excluirAula/{aulaId}/{turmaId}")
    public String excluirAula(@PathVariable Long aulaId, @PathVariable Long turmaId, RedirectAttributes redirectAttributes) {
        try {
            aulaRepository.deleteById(aulaId); // Supondo que você tem AulaRepository injetado
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Aula excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao excluir aula: " + e.getMessage());
        }
        return "redirect:/ensalamento/turma/" + turmaId;
    }


}
