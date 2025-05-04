package com.barreirasapp.service;

import com.barreirasapp.dto.law.RegisterLawDTO;
import com.barreirasapp.dto.law.UpdateLawDTO;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.model.dao.DaoFactory;
import com.barreirasapp.model.dao.LawDao;
import com.barreirasapp.model.entities.Law;
import com.barreirasapp.model.entities.User;

import java.util.List;
import java.util.Map;

public class LawService {
    private final LawDao lawRepository;
    
    public LawService() {
        this.lawRepository= DaoFactory.createLawDao();
    }
    
    public void insert(RegisterLawDTO lawDTO) throws ValidationError {
        
        if (lawRepository.findById(lawDTO.getCode()) != null) {
            throw new ValidationError("Erro de integridade", Map.of("error", "Já existe um lei com esse código"));
        }
        
        Law law = new Law(
                lawDTO.getCode(),
                lawDTO.getDate(),
                lawDTO.getOfficialLink(),
                lawDTO.getTitle(),
                lawDTO.getDescription()
        );

        lawRepository.insert(law);
    }

    public void update(UpdateLawDTO updateLawDTO) throws ValidationError {

        Law lawToUpdate = lawRepository.findById(updateLawDTO.getCode());

        if (lawToUpdate == null) {
            throw new ValidationError("Erro de integridade", Map.of("error", "Lei com este código não existe"));
        }

        String lawOfficialLink = updateLawDTO.getOfficialLink();
        String lawDescription = updateLawDTO.getDescription();

        if (lawOfficialLink != null)
            lawToUpdate.setOfficialLink(lawOfficialLink);

        if (lawDescription != null)
            lawToUpdate.setDescription(lawDescription);

        this.lawRepository.update(lawToUpdate);
    }

    public void deleteById(String lawCode) throws ValidationError {

        if (lawRepository.findById(lawCode) == null) {
            throw new ValidationError("Erro de integridade", Map.of("error", "Lei com este código não existe"));
        }

        lawRepository.deleteById(lawCode);
    }

    public List<Law> listAll() {
        return lawRepository.findAll();
    }

    public Law findById(String code) {
        return lawRepository.findById(code);
    }
}
