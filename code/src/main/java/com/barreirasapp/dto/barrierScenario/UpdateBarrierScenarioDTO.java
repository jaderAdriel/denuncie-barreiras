package com.barreirasapp.dto.barrierScenario;

import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.entities.enums.BarrierType;
import jakarta.servlet.http.Part;

import java.util.HashMap;
import java.util.Map;

public class UpdateBarrierScenarioDTO {
    private int id;
    private String title;
    private String content;
    private BarrierType barrierType;
    private String[] associatedLawsIds;
    private Part filePart;

    Map<String, String> errors = new HashMap<>();

    public UpdateBarrierScenarioDTO(int id, String title, String content, String barrierType, String[] associatedLawsIds, Part filePart) throws ValidationError {
        this.setContent(content);
        this.setBarrierType(barrierType);
        this.setTitle(title);
        this.setAssociatedLawsIds(associatedLawsIds);
        this.setId(id);
        this.filePart = filePart;
    }

    public Part getFilePart() {
        return filePart;
    }

    private void setFilePart(Part filePart) {
        this.filePart = filePart;
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
            this.barrierType = BarrierType.fromValue(barrierType.toUpperCase());
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
