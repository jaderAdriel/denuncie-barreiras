package com.barreirasapp.model.dao;

import com.barreirasapp.model.entities.BarrierScenario;
import com.barreirasapp.model.entities.Law;

import java.util.List;

public interface LawBarrierScenarioAssociationDao {
    void associate(Law law, BarrierScenario barrierScenario);
    void dissociate(BarrierScenario scenario, Law law);
    void update(BarrierScenario scenario);
    List<Law> findByBarrierScenarioId(int barrierScenarioID);
}
