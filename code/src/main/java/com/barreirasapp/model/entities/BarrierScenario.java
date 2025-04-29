package com.barreirasapp.model.entities;

import java.time.LocalDate;
import java.util.List;

public class BarrierScenario {
    int id;
    String type;
    User author; //Mudar para tipo moderator
    String content;
    String title;
    LocalDate creationDate;
    List<User> likes;

    public BarrierScenario(List<User> likes, LocalDate creationDate, String title, String content, User author, String type) {
        this.likes = likes;
        this.creationDate = creationDate;
        this.title = title;
        this.content = content;
        this.author = author;
        this.type = type;
    }

    public BarrierScenario() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public List<User> getLikes() {
        return likes;
    }

    public void setLikes(List<User> likes) {
        this.likes = likes;
    }
}
