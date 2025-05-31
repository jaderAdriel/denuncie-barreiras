package com.barreirasapp.repositories;

import com.barreirasapp.entities.BarrierScenario;
import com.barreirasapp.entities.User;

import java.util.List;

public interface BarrierScenarioRepository extends GenericRepository<BarrierScenario, Integer> {

    void addLiker(User user, BarrierScenario barrierScenario);
    void removeLiker(User user, BarrierScenario barrierScenario);
    void persistComments(BarrierScenario barrierScenario);
    void persistAssociatedLaws(BarrierScenario barrierScenario);
    List<BarrierScenario> findAllByAuthorId(Integer id);
}
