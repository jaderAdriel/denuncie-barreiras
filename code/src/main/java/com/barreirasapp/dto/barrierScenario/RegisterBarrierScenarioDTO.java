package com.barreirasapp.dto.barrierScenario;

import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.model.entities.Law;
import com.barreirasapp.model.entities.User;
import com.barreirasapp.utils.Validator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterBarrierScenarioDTO {
    private String title;
    private String content;
    private String type;
    private User author;
    private List<Law> associatedLaws;
//    private ArrayList<File> files;
    Map<String, String> errors = new HashMap<>();

    public RegisterBarrierScenarioDTO(String title, String content, String type, User author, List<Law> associatedLaws) throws ValidationError {
        Map<String, String> requiredFields = new HashMap<>();
        requiredFields.put("content" , content);
        requiredFields.put("title", title);
        requiredFields.put("date", type);

        Validator.checkRequiredFields(requiredFields);

        this.setContent(content);
        this.setType(type);
        this.setTitle(title);
        this.setAuthor(author);
        this.associatedLaws = associatedLaws;

        if (!errors.isEmpty()) {
            throw new ValidationError("Campos inválidos", this.getErrors());
        }
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

    public String getType() {
        return type;
    }

    private void setType(String type) {
        this.type = type;
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


}
