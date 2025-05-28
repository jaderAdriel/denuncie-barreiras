package com.barreirasapp.model.dao;

import com.barreirasapp.model.entities.BarrierScenario;
import com.barreirasapp.model.entities.Comment;
import com.barreirasapp.model.entities.User;

import java.util.List;
import java.util.Set;

public interface BarrierScenarioLikeDao {
    void insert(User user, BarrierScenario barrierScenario);
    void deleteById(User user, BarrierScenario barrierScenario);
    Set<User> findByBarrierScenarioId(Integer barrierScenarioId);
}
