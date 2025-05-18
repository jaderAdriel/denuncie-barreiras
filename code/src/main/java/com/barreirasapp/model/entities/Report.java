package com.barreirasapp.model.entities;

import com.barreirasapp.model.dao.DaoFactory;
import com.barreirasapp.model.dao.ReportDao;
import com.barreirasapp.model.dao.UserDao;

public class Report {

    private int id;
    private String type;
    private String ambient;
    private Integer severity;
    private Boolean anonymousReport;
    private String eventDetailing;
    private User reporter;
    private BarrierScenario barrierScenario;

    public Report() {
    }

    public Report(String ambient, String eventDetailing, Boolean anonymousReport, BarrierScenario barrierScenario) {
        this.ambient = ambient;
        this.eventDetailing = eventDetailing;
        this.anonymousReport = anonymousReport;
        this.barrierScenario = barrierScenario;
    }

    public Report(int id, String type, String ambient, Integer severity, Boolean anonymousReport, String eventDetailing, User reporter, BarrierScenario barrierScenario) {
        this.id = id;
        this.type = type;
        this.ambient = ambient;
        this.severity = severity;
        this.anonymousReport = anonymousReport;
        this.eventDetailing = eventDetailing;
        this.reporter = reporter;
        this.barrierScenario = barrierScenario;
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

    public void save() {
        ReportDao dao = DaoFactory.createReportDao();
        dao.insert(this);
    }
}
