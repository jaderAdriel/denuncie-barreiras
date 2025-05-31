package com.barreirasapp.repositories;

import com.barreirasapp.entities.BarrierScenario;
import com.barreirasapp.entities.User;

import java.util.Set;

public interface BarrierScenarioLikeRepository {
    void insert(User user, BarrierScenario barrierScenario);
    void deleteById(User user, BarrierScenario barrierScenario);
    Set<User> findByBarrierScenarioId(Integer barrierScenarioId);
}
