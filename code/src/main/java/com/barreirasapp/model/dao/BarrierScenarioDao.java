package com.barreirasapp.model.dao;

import com.barreirasapp.model.entities.BarrierScenario;
import com.barreirasapp.model.entities.Comment;

import java.util.List;

public interface BarrierScenarioDao extends GenericDao<BarrierScenario, Integer>{
    List<BarrierScenario> findAllByAuthorId(Integer id);
    void insertComment(Comment comment, BarrierScenario barrierScenario);
}
