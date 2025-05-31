package com.barreirasapp.repositories;

import com.barreirasapp.entities.Moderator;
import com.barreirasapp.entities.User;
import com.barreirasapp.entities.valueobjects.Email;

import java.util.Optional;

public interface UserRepository extends GenericRepository<User, Integer> {
    Optional<User> findByEmail(Email email);
    Optional<Moderator> findModeratorById(Integer id);
}
