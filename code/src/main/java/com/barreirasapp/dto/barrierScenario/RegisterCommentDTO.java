package com.barreirasapp.dto.barrierScenario;

import com.barreirasapp.entities.User;

public class RegisterCommentDTO {
    private final String content;
    private final User author;
    private final Integer barrierScenarioId;

    public RegisterCommentDTO(String content, User author, Integer barrierScenarioId) {
        this.content = content;
        this.author = author;
        this.barrierScenarioId = barrierScenarioId;
    }

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }

    public Integer getBarrierScenarioId() {
        return barrierScenarioId;
    }
}
