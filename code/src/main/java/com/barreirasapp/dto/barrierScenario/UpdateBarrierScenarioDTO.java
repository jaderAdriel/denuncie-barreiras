package com.barreirasapp.dto.barrierScenario;

import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.model.entities.Law;
import com.barreirasapp.model.entities.User;
import com.barreirasapp.model.enums.BarrierType;
import com.barreirasapp.utils.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateBarrierScenarioDTO {
    private int id;
    private String title;
    private String content;
    private BarrierType barrierType;
    private String[] associatedLawsIds;
//    private ArrayList<File> files;
    Map<String, String> errors = new HashMap<>();

    public UpdateBarrierScenarioDTO(int id, String title, String content, String barrierType, String[] associatedLawsIds) throws ValidationError {
        this.setContent(content);
        this.setBarrierType(barrierType);
        this.setTitle(title);
        this.setAssociatedLawsIds(associatedLawsIds);
        this.setId(id);
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    private void setContent(String content) {
        this.content = content;
    }

    public BarrierType getBarrierType() {
        return barrierType;
    }

    private void setBarrierType(String barrierType) {
        try {
            this.barrierType = BarrierType.valueOf(barrierType.toUpperCase());
        } catch (Exception ignored) {
            errors.put("barrierType", "Valor inválido");
        }
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public String[] getAssociatedLawsIds() {
        return this.associatedLawsIds;
    }

    private void setAssociatedLawsIds(String[] associatedLawsIds) {
        this.associatedLawsIds = associatedLawsIds;
    }

    private void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
