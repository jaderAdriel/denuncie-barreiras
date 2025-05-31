package com.barreirasapp.entities;

import java.time.LocalDateTime;

public class ReportReview {
    private Moderator author;
    private LocalDateTime createAt;
    private Boolean isValid;
    private String comment;

    public ReportReview(Moderator author, LocalDateTime createAt, Boolean isValid, String comment) {
        this.author = author;
        this.createAt = createAt;
        this.isValid = isValid;
        this.comment = comment;
    }

    public ReportReview() {
    }

    public Moderator getAuthor() {
        return author;
    }

    public void setAuthor(Moderator author) {
        this.author = author;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}


