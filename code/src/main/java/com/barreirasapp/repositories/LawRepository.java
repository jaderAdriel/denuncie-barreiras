package com.barreirasapp.repositories;

import com.barreirasapp.entities.Law;

import java.util.List;

public interface LawRepository extends GenericRepository<Law, String> {
    List<Law> findByBarrierScenario(int barrierScenarioID);
}
