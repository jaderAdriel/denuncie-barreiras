package com.barreirasapp.model.dao;

import java.util.List;

public interface GenericDao<T, IdType>{
    void insert(T user);
    void update(T user);
    void deleteById(IdType id);
    T findById(IdType id);
    List<T> findAll();
}