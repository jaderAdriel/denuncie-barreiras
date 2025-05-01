package com.barreirasapp.model;

import com.barreirasapp.model.entities.User;

import java.util.UUID;

public class Session {
    private String id = UUID.randomUUID().toString();
    private User user;

    public Session(String id, User user) {
        this.id = id;
        this.user = user;
    }

    public Session(User user) {
        this.user = user;
        this.id = UUID.randomUUID().toString();
    }

    public int getUserId() {
        return this.user.getId();
    }

    public String getSessionId() {
        return this.id;
    }
}
