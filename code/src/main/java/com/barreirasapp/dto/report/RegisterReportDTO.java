package com.barreirasapp.dto.report;

import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.model.entities.User;
import com.barreirasapp.utils.Validator;
import java.util.HashMap;
import java.util.Map;

public class RegisterReportDTO {
    private String environment;
    private String incidentDetails;
    private Integer relatedScenarioId;
    private Boolean anonymous;
    private User reporter;

    Map<String, String> errors = new HashMap<>();

    public RegisterReportDTO( String environment, String incidentDetails, String relatedScenarioId, String anonymous) throws ValidationError {
        Map<String, String> requiredFields = new HashMap<>();
        requiredFields.put("environment", environment);
        requiredFields.put("incidentDetails", incidentDetails);
        requiredFields.put("anonymous", anonymous);

        Validator.checkRequiredFields(requiredFields);

        this.setEnvironment(environment);
        this.setIncidentDetails(incidentDetails);
        this.setRelatedScenarioId(relatedScenarioId);
        this.setAnonymous(anonymous);

        if (!errors.isEmpty()) {
            throw new ValidationError("Campos inválidos", this.getErrors());
        }
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
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

    public Boolean getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(String anonymous) {
        try{
            this.anonymous = Boolean.valueOf(anonymous);
        } catch (Exception e) {
            errors.put("relatedScenarioId", "Valor inválido");
        }
    }

    public Map<String, String> getErrors() {
        return errors;
    }

}
