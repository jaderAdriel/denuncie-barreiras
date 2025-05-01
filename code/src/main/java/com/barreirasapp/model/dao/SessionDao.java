package com.barreirasapp.model.dao;

import com.barreirasapp.model.Session;
import com.barreirasapp.model.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface SessionDao {

    Optional<Session> findByid (String sessionId);
    void insert(Session session);
    void deleteById (String sessionId);
}
