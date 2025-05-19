package com.barreirasapp.model.entities;

import com.barreirasapp.model.dao.DaoFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BarrierScenario {
    int id;
    String type;
    User author; //Mudar para tipo moderator
    String content;
    String title;
    LocalDateTime creationDate;
    List<User> likes;
    List<Law> associatedLaws;

    public BarrierScenario(String title, String content, String type, User author) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.author = author;
        this.associatedLaws = new ArrayList<>();
    }

    public BarrierScenario(int id, String type, User author, String content, String title, LocalDateTime creationDate, List<Law> associatedLaws ) {
        this.id = id;
        this.type = type;
        this.author = author;
        this.content = content;
        this.title = title;
        this.creationDate = creationDate;
        this.associatedLaws = associatedLaws;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public List<Law> getAssociatedLaws() {
        return associatedLaws;
    }

    public void setAssociatedLaws(List<Law> associatedLaws) {
        this.associatedLaws = associatedLaws;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public List<User> getLikes() {
        return likes;
    }

    public void setLikes(List<User> likes) {
        this.likes = likes;
    }

    public void save() {
        DaoFactory.createBarrierScenario().insert(this);
    }
}
