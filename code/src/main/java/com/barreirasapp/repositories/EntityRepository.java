package com.barreirasapp.repositories;

import com.barreirasapp.entities.Entity;
import com.barreirasapp.entities.Report;
import com.barreirasapp.entities.enums.EntityType;

import java.util.Optional;

public interface EntityRepository extends GenericRepository<Entity, Integer>{
    void deleteByCnpj(String cnpj);
    Optional<Entity> findByCnpj(String cnpj);

    Optional<Entity> findByType(EntityType type);
}
