package com.barreirasapp.dto.report;

import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.model.entities.User;
import com.barreirasapp.model.enums.BarrierType;
import com.barreirasapp.model.enums.EnvironmentType;
import com.barreirasapp.utils.Validator;
import java.util.HashMap;
import java.util.Map;

public class RegisterReportDTO {
    private EnvironmentType environment;
    private String incidentDetails;
    private Integer relatedScenarioId;
    private Boolean anonymous;
    private User reporter;
    private BarrierType type;

    Map<String, String> errors = new HashMap<>();

    public RegisterReportDTO(String environment, String incidentDetails, String relatedScenarioId, String anonymous, String type) throws ValidationError {
        Map<String, String> requiredFields = new HashMap<>();
        requiredFields.put("environment", environment);
        requiredFields.put("incidentDetails", incidentDetails);
        requiredFields.put("anonymous_report", anonymous);

        Validator.checkRequiredFields(requiredFields);

        this.setEnvironment(environment);
        this.setIncidentDetails(incidentDetails);
        this.setRelatedScenarioId(relatedScenarioId);
        this.setAnonymous(anonymous);
        this.setType(type);

        if (!errors.isEmpty()) {
            throw new ValidationError("Campos inválidos", this.getErrors());
        }
    }

    public EnvironmentType getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        if(environment == null || environment.isEmpty()){
            this.environment = null;
            return;
        }

        try {
            this.environment = EnvironmentType.valueOf(environment);
        } catch (NumberFormatException e) {
            errors.put("environment", "Valor inválido");
        }
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
        if(anonymous == null || anonymous.isEmpty()){
            this.anonymous = false;
        }

        try{
            this.anonymous = Boolean.valueOf(anonymous);
        } catch (Exception e) {
            errors.put("relatedScenarioId", "Valor inválido");
        }
    }

    public BarrierType getType() {
        return type;
    }

    public void setType(String type) {
        try {
            this.type = BarrierType.valueOf(type);
        } catch (NumberFormatException e) {
            errors.put("type", "Valor inválido");
        }
    }

    public Map<String, String> getErrors() {
        return errors;
    }

}
