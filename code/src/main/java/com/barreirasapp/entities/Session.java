package com.barreirasapp.entities;

import com.barreirasapp.entities.enums.UserRole;

import java.util.UUID;

public class Session {
    private String id = UUID.randomUUID().toString();
    private final User user;

    public Session(String id, User user) {
        this.id = id;
        this.user = user;
    }

    public Session(User user) {
        this.user = user;
        this.id = UUID.randomUUID().toString();
    }

    private UserRole getUserRole() {
        return user.getRole();
    }

    public User getUser() {
        return user;
    }

    public int getUserId() {
        return this.user.getId();
    }

    public String getSessionId() {
        return this.id;
    }
}
