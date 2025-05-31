package com.barreirasapp.repositories;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T, IdType>{
    IdType insert(T obj);
    void update(T obj);
    void deleteById(IdType id);
    Optional<T> findById(IdType id);
    List<T> findAll();
}