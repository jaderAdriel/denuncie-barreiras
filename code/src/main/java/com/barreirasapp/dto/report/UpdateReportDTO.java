package com.barreirasapp.dto.report;

import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.model.entities.User;
import com.barreirasapp.utils.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class UpdateReportDTO {
    private String environment;
    private String incidentDetails;
    private Integer relatedScenarioId;
    private User reporter;
    private Integer id;

    Map<String, String> errors = new HashMap<>();

    public UpdateReportDTO(String environment, String incidentDetails, String relatedScenarioId) throws ValidationError {
        Map<String, String> requiredFields = new HashMap<>();
        requiredFields.put("environment", environment);
        requiredFields.put("incidentDetails", incidentDetails);
        requiredFields.put("relatedScenarioId", relatedScenarioId);
        Validator.checkRequiredFields(requiredFields);

        this.setEnvironment(environment);
        this.setIncidentDetails(incidentDetails);
        this.setRelatedScenarioId(relatedScenarioId);
    }



    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getIncidentDetails() {
        return incidentDetails;
    }

    public void setIncidentDetails(String incidentDetails) {
        this.incidentDetails = incidentDetails;
    }

    public Integer getRelatedScenarioId() {
        return relatedScenarioId;
    }

    public void setRelatedScenarioId(String relatedScenarioId) {
        if(relatedScenarioId == null || relatedScenarioId.isEmpty()){
            this.relatedScenarioId = null;
            return;
        }

        try {
            this.relatedScenarioId = Integer.valueOf(relatedScenarioId);
        } catch (NumberFormatException e) {
            errors.put("relatedScenarioId", "Valor inválido");
        }
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(String id) {
        if(id == null || id.isEmpty()){
            this.id = null;
            return;
        }

        try {
            this.id = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            errors.put("id", "Valor inválido");
        }
    }

    public Map<String, String> getErrors() {
        return errors;
    }

}
