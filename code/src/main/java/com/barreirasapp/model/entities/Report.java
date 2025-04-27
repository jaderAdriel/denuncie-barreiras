package com.barreirasapp.model.entities;

public class Report {

    private String type;
    private String ambient;
    private String adress;
    private Integer severity;
    private Boolean anonymousReport;
    private String eventDetailing;
    private String relatedScenario; //Alterar para o tipo Objeto CenarioBarreira
    private User reporter;

    public Report(String type, String ambient, String adress, Integer severity, Boolean anonymousReport, String eventDetailing, String relatedScenario, User reporter) {
        this.type = type;
        this.ambient = ambient;
        this.adress = adress;
        this.severity = severity;
        this.anonymousReport = anonymousReport;
        this.eventDetailing = eventDetailing;
        this.relatedScenario = relatedScenario;
        this.reporter = reporter;
    }

    public Report() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmbient() {
        return ambient;
    }

    public void setAmbient(String ambient) {
        this.ambient = ambient;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Integer getSeverity() {
        return severity;
    }

    public void setSeverity(Integer severity) {
        this.severity = severity;
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

    public String getRelatedScenario() {
        return relatedScenario;
    }

    public void setRelatedScenario(String relatedScenario) {
        this.relatedScenario = relatedScenario;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }
}
