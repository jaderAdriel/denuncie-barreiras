package com.barreirasapp.model.dao;

import java.util.List;

public interface GenericDao<T, IdType>{
    void insert(T obj);
    void update(T obj);
    void deleteById(IdType id);
    T findById(IdType id);
    List<T> findAll();
}