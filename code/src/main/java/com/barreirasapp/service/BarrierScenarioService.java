package com.barreirasapp.service;

import com.barreirasapp.dto.barrierScenario.RegisterBarrierScenarioDTO;
import com.barreirasapp.dto.barrierScenario.RegisterCommentDTO;
import com.barreirasapp.dto.barrierScenario.UpdateBarrierScenarioDTO;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.model.dao.BarrierScenarioDao;
import com.barreirasapp.model.dao.DaoFactory;
import com.barreirasapp.model.dao.FileDao;
import com.barreirasapp.model.entities.BarrierScenario;
import com.barreirasapp.model.entities.Law;
import com.barreirasapp.model.entities.User;
import com.barreirasapp.model.enums.BarrierType;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class BarrierScenarioService {

    private final BarrierScenarioDao barrierScenarioRepository;
    private final LawService lawService;
    private final FileDao fileDao;

    public BarrierScenarioService() {
        lawService = new LawService();
        fileDao = DaoFactory.createFileDao();
        barrierScenarioRepository = DaoFactory.createBarrierScenario();
    }

    public void insert(RegisterBarrierScenarioDTO scenarioDTO) throws ValidationError {

        BarrierScenario scenario = new BarrierScenario(
                scenarioDTO.getTitle(),
                scenarioDTO.getContent(),
                scenarioDTO.getBarrierType(),
                scenarioDTO.getAuthor()
        );

        if (scenarioDTO.getFilePart() != null) {
            File file = fileDao.insert(scenarioDTO.getFilePart());
            scenario.setImageCoverPath(file.getName());
        }

        for (String lawCode : scenarioDTO.getAssociatedLawsIds()) {
            Optional<Law> law = lawService.findById(lawCode);
            law.ifPresent(scenario::associateLaw);
        }

        scenario.save();
    }

    public void delete(Integer barrierScenarioId) throws ValidationError {
        this.barrierScenarioRepository.deleteById(barrierScenarioId);
    }

    public void update(UpdateBarrierScenarioDTO scenarioDTO) throws ValidationError {

        BarrierScenario barrierScenario = BarrierScenario.find(scenarioDTO.getId())
                .orElseThrow(() -> new ValidationError(
                        "Erro de integridade",
                        Map.of("error", "Barreira com este código não existe"))
                );

        if (scenarioDTO.getFilePart() != null) {
            File file = fileDao.insert(scenarioDTO.getFilePart());
            barrierScenario.setImageCoverPath(file.getName());
        }

        List<Law> associatedLaws = new ArrayList<>();

        if (scenarioDTO.getAssociatedLawsIds() != null) {
            for (String lawCode : scenarioDTO.getAssociatedLawsIds()) {
                Optional<Law> law = lawService.findById(lawCode);
                law.ifPresent(associatedLaws::add);
            }
        }

        barrierScenario.setAssociatedLaws(associatedLaws);

        barrierScenario.update(scenarioDTO);

        System.out.println("Entrou aqui fim");
    }

    public List<BarrierScenario> listAll() {
        return barrierScenarioRepository.findAll();
    }

    public Optional<BarrierScenario> findById(int id) {
        return Optional.ofNullable(barrierScenarioRepository.findById(id));
    }

    public void addComment(RegisterCommentDTO commentDTO) throws ValidationError {
        Integer barrierScenarioId = commentDTO.getBarrierScenarioId();

        BarrierScenario barrierScenario = BarrierScenario.find(barrierScenarioId)
                .orElseThrow(() -> new ValidationError(
                        "Erro de integridade",
                        Map.of("error", "Barreira com este código não existe"))
                );

        barrierScenario.addComment(
                commentDTO.getContent(),
                commentDTO.getAuthor(),
                LocalDateTime.now()
        );
    }

    public void removeComment(User user, Integer barrierScenarioId,  Integer commentId) throws ValidationError {

        BarrierScenario barrierScenario = BarrierScenario.find(barrierScenarioId)
                .orElseThrow(() -> new ValidationError(
                        "Erro de integridade",
                        Map.of("error", "Barreira com este código não existe"))
                );

        barrierScenario.removeComment(commentId);
    }

    public void toogleLike(User user, Integer barrierScenarioId) throws ValidationError {
        BarrierScenario barrierScenario = BarrierScenario.find(barrierScenarioId)
                .orElseThrow(() -> new ValidationError(
                        "Erro de integridade",
                        Map.of("error", "Barreira com este código não existe"))
                );

        boolean alreadyLiked = barrierScenario.isLikedByUser(user.getId());

        if (alreadyLiked) {
            barrierScenario.removeLiker(user);
        } else {
            barrierScenario.addLiker(user);
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
