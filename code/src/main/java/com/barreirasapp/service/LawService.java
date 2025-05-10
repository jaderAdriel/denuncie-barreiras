package com.barreirasapp.service;

import com.barreirasapp.dto.law.RegisterLawDTO;
import com.barreirasapp.dto.law.UpdateLawDTO;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.model.dao.DaoFactory;
import com.barreirasapp.model.dao.LawDao;
import com.barreirasapp.model.entities.Law;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LawService {
    
    public LawService() {
    }
    
    public void insert(RegisterLawDTO lawDTO) throws ValidationError {
        
        if (Law.find(lawDTO.getCode()).isPresent()) {
            throw new ValidationError("Erro de integridade", Map.of("error", "Já existe um lei com esse código"));
        }
        
        Law law = new Law(
                lawDTO.getCode(),
                lawDTO.getDate(),
                lawDTO.getOfficialLink(),
                lawDTO.getTitle(),
                lawDTO.getDescription()
        );

        law.save();
    }

    public void update(UpdateLawDTO updateLawDTO) throws ValidationError {

        Optional<Law> lawOptional = Law.find(updateLawDTO.getCode());

        if (lawOptional.isEmpty()) {
            throw new ValidationError("Erro de integridade", Map.of("error", "Lei com este código não existe"));
        }

        Law lawToUpdate = lawOptional.get();

        String lawOfficialLink = updateLawDTO.getOfficialLink();
        String lawDescription = updateLawDTO.getDescription();

        if (lawOfficialLink != null)
            lawToUpdate.setOfficialLink(lawOfficialLink);

        if (lawDescription != null)
            lawToUpdate.setDescription(lawDescription);

        lawToUpdate.update();
    }

    public void deleteById(String lawCode) throws ValidationError {
        Optional<Law> lawToDelete = Law.find(lawCode);

        if (lawToDelete.isEmpty()) {
            throw new ValidationError("Erro de integridade", Map.of("error", "Lei com este código não existe"));
        }

        lawToDelete.get().delete();
    }

    public List<Law> listAll() {
        return Law.findAll();
    }

    public Optional<Law> findById(String code) {
        return Law.find(code);
    }
}
