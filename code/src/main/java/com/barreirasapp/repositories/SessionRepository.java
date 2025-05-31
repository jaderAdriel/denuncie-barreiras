package com.barreirasapp.repositories;

import com.barreirasapp.entities.Session;
import com.barreirasapp.entities.User;

import java.util.Optional;

public interface SessionRepository {

    Optional<Session> findById (String sessionId);
    void insert(Session session);
    void deleteById (String sessionId);
    void deleteAllByUser(User user);
}
