package com.barreirasapp.services;

import com.barreirasapp.dto.barrierScenario.RegisterBarrierScenarioDTO;
import com.barreirasapp.dto.barrierScenario.RegisterCommentDTO;
import com.barreirasapp.dto.barrierScenario.UpdateBarrierScenarioDTO;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.repositories.BarrierScenarioRepository;
import com.barreirasapp.repositories.FileRepository;
import com.barreirasapp.entities.BarrierScenario;
import com.barreirasapp.entities.Law;
import com.barreirasapp.entities.User;
import com.barreirasapp.entities.enums.BarrierType;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class BarrierScenarioService {

    private final BarrierScenarioRepository barrierScenarioRepository;
    private final LawService lawService;
    private final FileRepository fileRepository;

    public BarrierScenarioService(BarrierScenarioRepository barrierScenarioRepository, FileRepository fileRepository, LawService lawService) {
        this.barrierScenarioRepository = barrierScenarioRepository;
        this.lawService = lawService;
        this.fileRepository = fileRepository;
    }

    public void insert(RegisterBarrierScenarioDTO scenarioDTO) throws ValidationError {

        BarrierScenario scenario = new BarrierScenario(
                scenarioDTO.getTitle(),
                scenarioDTO.getContent(),
                scenarioDTO.getBarrierType(),
                scenarioDTO.getAuthor()
        );

        if (scenarioDTO.getFilePart() != null) {
            File file = fileRepository.insert(scenarioDTO.getFilePart());
            scenario.setImageCoverPath(file.getName());
        }

        try {
            for (String lawCode : scenarioDTO.getAssociatedLawsIds()) {
                Optional<Law> law = lawService.findById(lawCode);
                law.ifPresent(scenario::associateLaw);
            }
        } catch (Exception ignore){};

        barrierScenarioRepository.insert(scenario);
    }

    public void delete(Integer barrierScenarioId) throws ValidationError {
        this.barrierScenarioRepository.deleteById(barrierScenarioId);
    }

    public void update(UpdateBarrierScenarioDTO scenarioDTO) throws ValidationError {

        BarrierScenario barrierScenario = barrierScenarioRepository.findById(scenarioDTO.getId())
                .orElseThrow(() -> new ValidationError(
                        "Erro de integridade",
                        Map.of("error", "Barreira com este código não existe"))
                );

        if (scenarioDTO.getFilePart() != null) {
            File file = fileRepository.insert(scenarioDTO.getFilePart());
            barrierScenario.setImageCoverPath(file.getName());
        }

        Set<Law> associatedLaws = new HashSet<>();

        if (scenarioDTO.getAssociatedLawsIds() != null) {
            for (String lawCode : scenarioDTO.getAssociatedLawsIds()) {
                Optional<Law> law = lawService.findById(lawCode);
                law.ifPresent(associatedLaws::add);
            }
        }

        barrierScenario.setAssociatedLaws(associatedLaws);
        barrierScenario.update(scenarioDTO);

        barrierScenarioRepository.update(barrierScenario);
    }

    public List<BarrierScenario> listAll() {
        return barrierScenarioRepository.findAll();
    }

    public Optional<BarrierScenario> findById(int id) {
        return barrierScenarioRepository.findById(id);
    }

    public void addComment(RegisterCommentDTO commentDTO) throws ValidationError {
        Integer barrierScenarioId = commentDTO.getBarrierScenarioId();

        BarrierScenario barrierScenario = barrierScenarioRepository.findById(barrierScenarioId)
                .orElseThrow(() -> new ValidationError(
                        "Erro de integridade",
                        Map.of("error", "Barreira com este código não existe"))
                );

        barrierScenario.addComment(
                commentDTO.getContent(),
                commentDTO.getAuthor(),
                LocalDateTime.now()
        );

        barrierScenarioRepository.persistComments(barrierScenario);
    }

    public void removeComment(User user, Integer barrierScenarioId,  Integer commentId) throws ValidationError {

        BarrierScenario barrierScenario = barrierScenarioRepository.findById(barrierScenarioId)
                .orElseThrow(() -> new ValidationError(
                        "Erro de integridade",
                        Map.of("error", "Barreira com este código não existe"))
                );

        barrierScenario.removeComment(commentId);
        barrierScenarioRepository.persistComments(barrierScenario);
    }

    public void toogleLike(User user, Integer barrierScenarioId) throws ValidationError {
        BarrierScenario barrierScenario = this.barrierScenarioRepository.findById(barrierScenarioId)
                .orElseThrow(() -> new ValidationError(
                        "Erro de integridade",
                        Map.of("error", "Barreira com este código não existe"))
                );

        boolean alreadyLikedByUser = barrierScenario.isLikedByUser(user.getId());

        if (alreadyLikedByUser) {
            barrierScenario.removeLiker(user);
            barrierScenarioRepository.removeLiker(user, barrierScenario);
        } else {
            barrierScenario.addLiker(user);
            barrierScenarioRepository.addLiker(user, barrierScenario);
        }
    }

    public List<BarrierScenario> filterAndSearch(String[] barrierTypeNames, String[] lawCodes, String searchTerm) {
        List<BarrierScenario> filteredList = barrierScenarioRepository.findAll();

        // 1. Filtrar por Tipo de Barreira
        if (barrierTypeNames != null && barrierTypeNames.length > 0) {
            List<BarrierType> selectedBarrierTypes = Arrays.stream(barrierTypeNames)
                    .map(name -> {
                        try {
                            return BarrierType.valueOf(name);
                        } catch (IllegalArgumentException e) {
                            System.err.println("Tipo de barreira inválido recebido: " + name);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();

            if (!selectedBarrierTypes.isEmpty()) {
                filteredList = filteredList.stream()
                        .filter(scenario -> selectedBarrierTypes.contains(scenario.getBarrierType()))
                        .collect(Collectors.toList());
            }
        }

        // 2. Filtrar por Legislação Relacionada
        if (lawCodes != null && lawCodes.length > 0) {
            List<String> selectedLawCodes = Arrays.asList(lawCodes); // Converte para lista para contains
            filteredList = filteredList.stream()
                    .filter(scenario -> scenario.getAssociatedLaws().stream()
                            .anyMatch(law -> selectedLawCodes.contains(law.getCode())))
                    .collect(Collectors.toList());
        }

        // 3. Filtrar por Termo de Busca (Título)
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            String lowerCaseSearchTerm = searchTerm.trim().toLowerCase();
            filteredList = filteredList.stream()
                    .filter(scenario -> scenario.getTitle().toLowerCase().contains(lowerCaseSearchTerm))
                    .collect(Collectors.toList());
        }

        return filteredList;
    }
}
