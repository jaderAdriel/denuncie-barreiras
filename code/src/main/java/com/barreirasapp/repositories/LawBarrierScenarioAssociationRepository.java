package com.barreirasapp.repositories;

import com.barreirasapp.entities.BarrierScenario;
import com.barreirasapp.entities.Law;

import java.util.Set;

public interface LawBarrierScenarioAssociationRepository {
    void associate(Law law, BarrierScenario barrierScenario);
    void dissociate(BarrierScenario scenario, Law law);
    void persistAssociatedLaws(BarrierScenario scenario);
    Set<Law> findByBarrierScenarioId(int barrierScenarioID);
}
