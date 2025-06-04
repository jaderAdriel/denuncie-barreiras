package com.barreirasapp.dto;

import com.barreirasapp.entities.User;

import java.util.HashMap;
import java.util.Map;

public class RegisterReportReviewDTO {
    private String comment;
    private Boolean reportIsValid;
    private User reviewAuthor;
    private Integer reportId;

    Map<String, String> errors = new HashMap<>();

    public RegisterReportReviewDTO(String comment, String reportIsValid, User reviewAuthor, String reportId) {
        this.comment = comment;
        setReportId(reportId);
        setComment(comment);
        setReportIsValid(reportIsValid);
        this.reviewAuthor = reviewAuthor;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setReportIsValid(String reportIsValid) {
        if(reportIsValid == null || reportIsValid.isEmpty()){
            this.reportIsValid = false;
        }

        try{
            this.reportIsValid = Boolean.valueOf(reportIsValid);
        } catch (Exception e) {
            errors.put("isValid", "Valor inválido");
        }
    }


    public void setReportId(String reportId) {
        if(reportId == null || reportId.isEmpty()){
            this.reportId = null;
            return;
        }

        try {
            this.reportId = Integer.valueOf(reportId);
        } catch (NumberFormatException e) {
            errors.put("relatedScenarioId", "Valor inválido");
        }
    }

    public String getComment() {
        return comment;
    }

    public Boolean getReportIsValid() {
        return reportIsValid;
    }

    public User getReviewAuthor() {
        return reviewAuthor;
    }

    public void setReviewAuthor(User reviewAuthor) {
        this.reviewAuthor = reviewAuthor;
    }

    public Integer getReportId() {
        return reportId;
    }
}
