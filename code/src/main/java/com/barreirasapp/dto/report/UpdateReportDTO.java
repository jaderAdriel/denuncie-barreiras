package com.barreirasapp.dto.report;

import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.model.entities.Moderator;
import com.barreirasapp.model.entities.User;
import com.barreirasapp.model.enums.EnvironmentType;
import com.barreirasapp.utils.Validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class UpdateReportDTO {
    private Integer id;
    private Moderator reviewAuthor;
    private Boolean reviewIsValid;
    private String reviewComment;

    Map<String, String> errors = new HashMap<>();

    public UpdateReportDTO(String id, String reviewIsValid, String reviewComment, Moderator reviewAuthor) {
        this.id = Integer.parseInt(id);
        this.reviewAuthor = reviewAuthor;
        this.reviewIsValid = true;
        this.reviewComment = reviewComment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Moderator getReviewAuthor() {
        return reviewAuthor;
    }

    public void setReviewAuthor(Moderator reviewAuthor) {
        this.reviewAuthor = reviewAuthor;
    }

    public Boolean getReviewIsValid() {
        return reviewIsValid;
    }

    public void setReviewIsValid(Boolean reviewIsValid) {
        this.reviewIsValid = reviewIsValid;
    }

    public String getReviewComment() {
        return reviewComment;
    }

    public void setReviewComment(String reviewComment) {
        this.reviewComment = reviewComment;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

}
