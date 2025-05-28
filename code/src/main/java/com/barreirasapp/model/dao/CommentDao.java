package com.barreirasapp.model.dao;

import com.barreirasapp.model.entities.BarrierScenario;
import com.barreirasapp.model.entities.Comment;
import com.barreirasapp.model.entities.User;

import java.util.List;

public interface CommentDao{
    void insert(Comment comment, BarrierScenario barrierScenario);
    List<Comment> findByBarrierScenarioId(Integer barrierScenarioId);
    void deleteById(Integer id);

}
