package com.barreirasapp.service;

import com.barreirasapp.dto.barrierScenario.RegisterBarrierScenarioDTO;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.model.dao.BarrierScenarioDao;
import com.barreirasapp.model.dao.DaoFactory;
import com.barreirasapp.model.entities.BarrierScenario;
import java.util.List;

public class BarrierScenarioService {

    BarrierScenarioDao barrierScenarioRepository;

    public BarrierScenarioService() {
        barrierScenarioRepository = DaoFactory.createBarrierScenario();
    }

    public void insert(RegisterBarrierScenarioDTO scenarioDTO) throws ValidationError {

        BarrierScenario scenario = new BarrierScenario(
                scenarioDTO.getTitle(),
                scenarioDTO.getContent(),
                scenarioDTO.getType(),
                scenarioDTO.getAuthor()
        );

        scenario.save();
    }

    public List<BarrierScenario> listAll() {
        return barrierScenarioRepository.findAll();
    }

    public BarrierScenario findById(int id) throws ValidationError {
        return barrierScenarioRepository.findById(id);
    }
}
