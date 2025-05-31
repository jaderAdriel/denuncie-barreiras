package com.barreirasapp.repositories;

import com.barreirasapp.entities.BarrierScenario;
import com.barreirasapp.entities.Comment;

import java.util.Set;

public interface CommentRepository {
    void insert(Comment comment, BarrierScenario barrierScenario);
    Set<Comment> findByBarrierScenarioId(Integer barrierScenarioId);
    void deleteById(Integer id);
    void persistComments(BarrierScenario barrierScenario);
}
