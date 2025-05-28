package com.barreirasapp.dto.barrierScenario;

import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.model.entities.User;
import com.barreirasapp.model.enums.BarrierType;
import com.barreirasapp.utils.Validator;
import jakarta.servlet.http.Part;

import java.util.HashMap;
import java.util.Map;

public class RegisterBarrierScenarioDTO {
    private String title;
    private String content;
    private BarrierType barrierType;
    private User author;
    private String[] associatedLawsIds;
    private Part filePart;
    Map<String, String> errors = new HashMap<>();

    public RegisterBarrierScenarioDTO(String title, String content, String barrierType, User author, String[] associatedLawsIds, Part filePart) throws ValidationError {
        Map<String, String> requiredFields = new HashMap<>();
        requiredFields.put("content" , content);
        requiredFields.put("title", title);
        requiredFields.put("barrierType", barrierType);

        Validator.checkRequiredFields(requiredFields);

        this.setContent(content);
        this.setBarrierType(barrierType);
        this.setTitle(title);
        this.setAuthor(author);
        this.setAssociatedLawsIds(associatedLawsIds);
        this.filePart = filePart;
    }

    public String getTitle() {
        return title;
    }

    public Part getFilePart() {
        return filePart;
    }

    private void setFilePart(Part filePart) {
        this.filePart = filePart;
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
            errors.put("barrierTypeError", "Valor inválido");
        }
    }

    public User getAuthor() {
        return author;
    }

    private void setAuthor(User author) {
        this.author = author;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public String[] getAssociatedLawsIds() {
        return associatedLawsIds;
    }

    private void setAssociatedLawsIds(String[] associatedLawsIds) {
        this.associatedLawsIds = associatedLawsIds;
    }
}
