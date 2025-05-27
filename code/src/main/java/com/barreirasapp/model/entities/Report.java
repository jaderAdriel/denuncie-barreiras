package com.barreirasapp.model.entities;

import com.barreirasapp.model.dao.DaoFactory;
import com.barreirasapp.model.dao.ReportDao;
import com.barreirasapp.model.dao.UserDao;
import com.barreirasapp.model.enums.BarrierType;
import com.barreirasapp.model.enums.EnvironmentType;

import java.time.LocalDate;
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

    public int getReporterId(){
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

    public void save() {
        ReportDao dao = DaoFactory.createReportDao();
        dao.insert(this);
    }
}
