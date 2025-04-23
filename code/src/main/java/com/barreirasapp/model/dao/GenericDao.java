package com.barreirasapp.model.dao;

import java.util.List;

public interface GenericDao<T>{
    void insert(T user);
    void update(T user);
    void deleteById(Integer id);
    T findById(Integer id);
    List<T> findAll();
}