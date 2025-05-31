package com.barreirasapp.entities;

import com.barreirasapp.dto.barrierScenario.UpdateBarrierScenarioDTO;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.entities.enums.BarrierType;

import java.time.LocalDateTime;
import java.util.*;

public class BarrierScenario {
    private Integer id;
    private BarrierType barrierType;
    private User author; //Mudar para tipo moderator
    private String content;
    private String title;
    private LocalDateTime creationDate;
    private Set<User> likes = new HashSet<>();
    private Set<Law> associatedLaws = new HashSet<>();
    private Set<Comment> comments = new HashSet<>();
    private String imageCoverPath;

    public BarrierScenario(String title, String content, BarrierType barrierType, User author) {
        this.title = title;
        this.content = content;
        this.barrierType = barrierType;
        this.author = author;
    }

    public BarrierScenario(int id, BarrierType barrierType, User author, String content, String title, LocalDateTime creationDate,
                           Set<Law> associatedLaws, Set<Comment> comments, Set<User> likes) {
        this.id = id;
        this.barrierType = barrierType ;
        this.author = author;
        this.content = content;
        this.title = title;
        this.creationDate = creationDate;
        this.associatedLaws = associatedLaws;
        this.comments = comments;
        this.likes = likes;
    }

    public String getImageCoverPath() {
        return imageCoverPath;
    }

    public void setImageCoverPath(String imageCoverPath) {
        this.imageCoverPath = imageCoverPath;
    }

    public BarrierScenario() {
    }

    public int getId() {
        return id;
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
        if (title == null || title.isEmpty()) {
            throw new ValidationError("Titúlo não pode ser vazio", new HashMap<>());
        };
        this.title = title;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Set<Law> getAssociatedLaws() {
        return associatedLaws;
    }

    public void setAssociatedLaws(Set<Law> associatedLaws) {
        this.associatedLaws = associatedLaws;
    }

    public List<User> getLikes() {
        return likes.stream().toList();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void update(UpdateBarrierScenarioDTO dto) throws ValidationError {

        this.setBarrierType(dto.getBarrierType());
        this.setContent(dto.getContent());
        this.setTitle(dto.getTitle());
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void addComment(String content, User user, LocalDateTime creationDate) {
        Comment comment = new Comment(content, user, creationDate);
        this.comments.add(comment);
    }

    public void removeComment(Integer commentId) {
        Comment comment = this.comments.stream()
                .filter(cm -> cm.getId() == commentId)
                .findFirst()
                .orElseThrow();

        this.comments.remove(comment);
    }

    public void addLiker(User user) {
        this.likes.add(user);
    }

    public void removeLiker(User user) {
        this.likes.add(user);
    }

    public boolean isLikedByUser(int id) {
        Optional<User> user = this.likes.stream()
                .filter(cm -> cm.getId() == id)
                .findFirst();

        return user.isPresent();
    }
}
