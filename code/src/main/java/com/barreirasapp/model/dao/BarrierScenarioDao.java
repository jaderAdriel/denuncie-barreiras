package com.barreirasapp.model.dao;

import com.barreirasapp.model.entities.BarrierScenario;

import java.util.List;

public interface BarrierScenarioDao extends GenericDao<BarrierScenario, Integer>{
    List<BarrierScenario> findAllByAuthorId(Integer id);
}
