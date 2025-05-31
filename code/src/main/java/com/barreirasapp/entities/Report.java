package com.barreirasapp.entities;

import com.barreirasapp.entities.enums.BarrierType;
import com.barreirasapp.entities.enums.EnvironmentType;

import java.time.LocalDateTime;

public class Report {

    private int id;
    private BarrierType type;
    private EnvironmentType ambient;
    private Boolean anonymousReport;
    private String eventDetailing;
    private User reporter;
    private BarrierScenario barrierScenario;
    private LocalDateTime creationDate;
    private ReportReview review;
    private Boolean published;

    public Report() {
    }

    public Report(EnvironmentType ambient, String eventDetailing, Boolean anonymousReport, BarrierScenario barrierScenario) {
        this.ambient = ambient;
        this.eventDetailing = eventDetailing;
        this.anonymousReport = anonymousReport;
        this.barrierScenario = barrierScenario;
    }

    public Report(int id, BarrierType type, EnvironmentType ambient, Boolean anonymousReport, String eventDetailing, User reporter, BarrierScenario barrierScenario, LocalDateTime creationDate) {
        this.id = id;
        this.type = type;
        this.ambient = ambient;
        this.anonymousReport = anonymousReport;
        this.eventDetailing = eventDetailing;
        this.reporter = reporter;
        this.barrierScenario = barrierScenario;
        this.creationDate = creationDate;
    }

    public void setReportReview(Moderator author,
     LocalDateTime createAt,
     Boolean isValid,
     String comment) {
        this.review = new ReportReview(author,
        createAt,
        isValid,
        comment);
    }

    public int getReviewAuthorId() {
        return this.review.getAuthor().getId();
    }

    public String getReviewComment() {
        return this.review.getComment();
    }

    public Boolean getReviewIsValid() {
        return this.review.getValid();
    }

    public LocalDateTime getReviewCreationDate() {
        return this.review.getCreateAt();
    }

    public BarrierType getType() {
        return type;
    }

    public void setType(BarrierType type) {
        this.type = type;
    }

    public EnvironmentType getAmbient() {
        return ambient;
    }

    public void setAmbient(EnvironmentType ambient) {
        this.ambient = ambient;
    }

    public Boolean getAnonymousReport() {
        return anonymousReport;
    }

    public void setAnonymousReport(Boolean anonymousReport) {
        this.anonymousReport = anonymousReport;
    }

    public String getEventDetailing() {
        return eventDetailing;
    }

    public void setEventDetailing(String eventDetailing) {
        this.eventDetailing = eventDetailing;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Integer getReporterId(){
        if (anonymousReport) return null;
        return this.reporter.getId();
    }

    public BarrierScenario getBarrierScenario() {
        return barrierScenario;
    }

    public void setBarrierScenario(BarrierScenario barrierScenario) {
        this.barrierScenario = barrierScenario;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getBarrierScenarioId() {
        if(barrierScenario == null){
            return null;
        }
        return barrierScenario.getId();
    }

    public Boolean getPublished() {
        return published;
    }

    public void isPublished(Boolean published) {
        this.published = published;
    }

    public ReportReview getReview() {
        return review;
    }

    public void setReview(ReportReview review) {
        this.review = review;
    }

}
