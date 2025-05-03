package com.barreirasapp.model.dao;

import com.barreirasapp.model.entities.User;
import com.barreirasapp.model.entities.valueobjects.Email;

import java.util.Optional;

public interface UserDao extends GenericDao<User, Integer>{
    Optional<User> getUserByEmail(Email email);
}
