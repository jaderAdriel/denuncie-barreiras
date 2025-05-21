package com.barreirasapp.model.entities;

import com.barreirasapp.dto.barrierScenario.UpdateBarrierScenarioDTO;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.model.dao.DaoFactory;
import com.barreirasapp.model.enums.BarrierType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class BarrierScenario {
    int id;
    BarrierType barrierType;
    User author; //Mudar para tipo moderator
    String content;
    String title;
    LocalDateTime creationDate;
    List<User> likes;
    List<Law> associatedLaws;

    public BarrierScenario(String title, String content, BarrierType barrierType, User author) {
        this.title = title;
        this.content = content;
        this.barrierType = barrierType;
        this.author = author;
        this.associatedLaws = new ArrayList<>();
    }

    public BarrierScenario(int id, BarrierType barrierType, User author, String content, String title, LocalDateTime creationDate, List<Law> associatedLaws ) {
        this.id = id;
        this.barrierType = barrierType ;
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

    public BarrierType getBarrierType() {
        return barrierType;
    }

    public void setBarrierType(BarrierType barrierType) {
        this.barrierType = barrierType;
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

    public void setContent(String content) throws ValidationError {
        if (content.isEmpty()) {
            throw new ValidationError("Conteúdo não pode ser vazio", new HashMap<>());
        };
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void associateLaw(Law law) {
        associatedLaws.add(law);
    }

    public void setTitle(String title) throws ValidationError {
        if (content.isEmpty()) {
            throw new ValidationError("Titúlo não pode ser vazio", new HashMap<>());
        };
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

    public void update(UpdateBarrierScenarioDTO dto) throws ValidationError {

        this.setBarrierType(dto.getBarrierType());

        this.setContent(dto.getContent());

        this.setTitle(dto.getTitle());

        DaoFactory.createBarrierScenario().update(this);
    }

    public static Optional<BarrierScenario> find(Integer id) {
        BarrierScenario barrierScenario = DaoFactory.createBarrierScenario().findById(id);

        return Optional.ofNullable(barrierScenario);
    }
}
