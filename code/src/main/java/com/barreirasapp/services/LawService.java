package com.barreirasapp.services;

import com.barreirasapp.repositories.LawRepository;
import com.barreirasapp.dto.law.RegisterLawDTO;
import com.barreirasapp.dto.law.UpdateLawDTO;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.entities.Law;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LawService {
    private final LawRepository lawRepository;

    public LawService(LawRepository lawRepository) {
        this.lawRepository = lawRepository;
    }

    public void insert(RegisterLawDTO lawDTO) throws ValidationError {

        lawRepository.findById(lawDTO.getCode())
                .ifPresent((e) -> {
                        throw new ValidationError("Erro de integridade", Map.of("error", "Já existe um lei com esse código"));
                    }
                );

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

        Optional<Law> lawOptional = lawRepository.findById(updateLawDTO.getCode());

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

        lawRepository.update(lawToUpdate);
    }

    public void deleteById(String lawCode) throws ValidationError {
        Law lawToDelete = lawRepository.findById(lawCode)
                .orElseThrow(() -> new ValidationError("Erro de integridade", Map.of("error", "Lei com este código não existe")));

        lawRepository.deleteById(lawToDelete.getCode());
    }

    public List<Law> listAll() {
        return lawRepository.findAll();
    }

    public Optional<Law> findById(String code) {
        return lawRepository.findById(code);
    }
}
