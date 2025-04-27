package com.barreirasapp.model.entities;

import com.barreirasapp.model.dao.DaoFactory;
import com.barreirasapp.model.dao.ReportDao;
import com.barreirasapp.model.dao.UserDao;

public class Report {

    private int id;
    private String type;
    private String ambient;
    private String address;
    private Integer severity;
    private Boolean anonymousReport;
    private String eventDetailing;
    private User reporter;

    public Report(String type, String ambient, String address, Integer severity, Boolean anonymousReport, String eventDetailing, User reporter) {
        this.type = type;
        this.ambient = ambient;
        this.address = address;
        this.severity = severity;
        this.anonymousReport = anonymousReport;
        this.eventDetailing = eventDetailing;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String adress) {
        this.address = adress;
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

    public void save() {
        ReportDao dao = DaoFactory.createReportDao();
        dao.insert(this);
    }
}
