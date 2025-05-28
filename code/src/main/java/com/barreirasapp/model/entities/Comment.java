package com.barreirasapp.model.entities;

import java.time.LocalDateTime;

public class Comment {
    private int id;
    private final LocalDateTime creationDateTime;
    private final String content;
    private final User author;

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

    public String getAuthorName()  {
        return this.author.getName();
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }

    public int getId() {
        return id;
    }
}
