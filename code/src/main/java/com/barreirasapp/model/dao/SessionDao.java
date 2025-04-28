package com.barreirasapp.model.dao;

import com.barreirasapp.model.Session;

import java.util.Optional;
import java.util.UUID;

public interface SessionDao {

    Optional<Session> findByid (String sessionId);
    void insert (Session session);
    void deleteById (String sessionId);
}
