package com.barreirasapp.service;

import com.barreirasapp.dto.barrierScenario.RegisterBarrierScenarioDTO;
import com.barreirasapp.dto.barrierScenario.UpdateBarrierScenarioDTO;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.model.dao.BarrierScenarioDao;
import com.barreirasapp.model.dao.DaoFactory;
import com.barreirasapp.model.entities.BarrierScenario;
import com.barreirasapp.model.entities.Law;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BarrierScenarioService {

    BarrierScenarioDao barrierScenarioRepository;
    LawService lawService;

    public BarrierScenarioService() {
        lawService = new LawService();
        barrierScenarioRepository = DaoFactory.createBarrierScenario();
    }

    public void insert(RegisterBarrierScenarioDTO scenarioDTO) throws ValidationError {

        BarrierScenario scenario = new BarrierScenario(
                scenarioDTO.getTitle(),
                scenarioDTO.getContent(),
                scenarioDTO.getBarrierType(),
                scenarioDTO.getAuthor()
        );

        for (String lawCode : scenarioDTO.getAssociatedLawsIds()) {
            Optional<Law> law = lawService.findById(lawCode);
            if (law.isPresent()) {
                scenario.associateLaw(law.get());
            }
        }

        scenario.save();
    }

    public void update(UpdateBarrierScenarioDTO scenarioDTO) throws ValidationError {

        Optional<BarrierScenario> barrierScenarioOptional = BarrierScenario.find(scenarioDTO.getId());

        if (barrierScenarioOptional.isEmpty()) {
            throw new ValidationError("Erro de integridade", Map.of("error", "Barreira com este código não existe"));
        }

        BarrierScenario barrierScenarioToUpdate = barrierScenarioOptional.get();


        List<Law> associatedLaws = new ArrayList<>();

        if (scenarioDTO.getAssociatedLawsIds() != null) {
            for (String lawCode : scenarioDTO.getAssociatedLawsIds()) {
                Optional<Law> law = lawService.findById(lawCode);
                law.ifPresent(associatedLaws::add);
            }
        }

        barrierScenarioToUpdate.setAssociatedLaws(associatedLaws);

        barrierScenarioToUpdate.update(scenarioDTO);
    }

    public List<BarrierScenario> listAll() {
        return barrierScenarioRepository.findAll();
    }

    public Optional<BarrierScenario> findById(int id) {
        return Optional.ofNullable(barrierScenarioRepository.findById(id));
    }
}
