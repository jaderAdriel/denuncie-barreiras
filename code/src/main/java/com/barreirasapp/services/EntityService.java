package com.barreirasapp.services;

import com.barreirasapp.dto.entity.RegisterEntityDTO;
import com.barreirasapp.entities.Entity;
import com.barreirasapp.entities.enums.EntityType;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.repositories.EntityRepository;
import com.barreirasapp.repositories.impl.EntityRepositoryJDBC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EntityService {

    private static final Logger log = LoggerFactory.getLogger(EntityService.class);
    private final EntityRepository entityRepository;

    public EntityService(EntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    public void register(RegisterEntityDTO entityDTO) throws ValidationError {
        if (entityDTO.getName() == null || entityDTO.getName().trim().isEmpty()) {
            throw new ValidationError("Erro de validação", Map.of("name", "O nome é obrigatório."));
        }
        if (entityDTO.getCnpj() == null || !entityDTO.getCnpj().matches("\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}")) {
            throw new ValidationError("Erro de validação", Map.of("cnpj", "CNPJ inválido. Use o formato 00.000.000/0000-00."));
        }

        EntityType type;
        try {
            type = EntityType.valueOf(entityDTO.getType().toString());
        } catch (IllegalArgumentException e) {
            throw new ValidationError("Erro de validação", Map.of("type", "Tipo de entidade inválido."));
        }

        Entity entity = new Entity(
                entityDTO.getName(),
                entityDTO.getCnpj(),
                type,
                entityDTO.getPhone()
        );

        entityRepository.insert(entity);
    }

    public List<Entity> listAll() {
        return entityRepository.findAll();
    }


    public Optional<Entity> findById(Integer id) {
        return entityRepository.findById(id);
    }


    public void deleteById(Integer id) {
        entityRepository.deleteById(id);
    }

    public Optional<Entity> findByType(String type) {
        return entityRepository.findByType(EntityType.valueOf(type));
    }

    public Optional<Entity> findByCnpj(String cnpj) {
        return entityRepository.findByCnpj(cnpj);
    }
}