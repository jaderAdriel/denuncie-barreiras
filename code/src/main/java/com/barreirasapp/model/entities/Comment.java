package com.barreirasapp.model.entities;

import java.time.LocalDateTime;

public class Comment {
    private int id;
    private LocalDateTime creationDateTime;
    private String content;
    private Comment responseTo;
    private User author;

    public Comment(String content, User author, LocalDateTime creationDateTime) {
        this.content = content;
        this.author = author;
        this.creationDateTime = creationDateTime;
    }

    public Comment(int id, String content, User author, LocalDateTime creationDateTime) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.creationDateTime = creationDateTime;
    }

    public void setResponseTo(Comment responseTo) {
        this.responseTo = responseTo;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public String getContent() {
        return content;
    }

    public Comment getResponseTo() {
        return responseTo;
    }

    public User getAuthor() {
        return author;
    }

    public int getId() {
        return id;
    }
}
