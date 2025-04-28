package com.barreirasapp.model;

import com.barreirasapp.model.entities.User;

import java.util.UUID;

public class Session {
    private String sessionId = UUID.randomUUID().toString();
    private User user;

    public Session(String sessionId, User user) {
        this.sessionId = sessionId;
        this.user = user;
    }

    public Session(User user) {
        this.user = user;
    }

    public int getUserId() {
        return this.user.getId();
    }

    public String getSessionId() {
        return this.sessionId;
    }
}
