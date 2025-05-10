package com.barreirasapp.model.entities;

import com.barreirasapp.model.dao.DaoFactory;
import com.barreirasapp.model.dao.UserDao;
import com.barreirasapp.model.enums.Gender;
import com.barreirasapp.model.entities.valueobjects.Email;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class User {
    private Integer id;
    private String name;
    private Email email;
    private LocalDate birthDate;
    private Gender gender;
    private String password;

    public User(String name, Email email, LocalDate birthDate, Gender gender, String password) {
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.gender = gender;
        this.password = password;
    }

    public User(Integer id, String password, Gender gender, LocalDate birthDate, Email email, String name) {
        this.id = id;
        this.password = password;
        this.gender = gender;
        this.birthDate = birthDate;
        this.email = email;
        this.name = name;
    }

    public User() {

    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Email getEmail() {
        return email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void save() {
        UserDao dao = DaoFactory.createUserDao();
        dao.insert(this);
    }

    public void update() {
        UserDao dao = DaoFactory.createUserDao();
        dao.update(this);
    }

    public void delete() {
        UserDao dao = DaoFactory.createUserDao();
        dao.deleteById(this.getId());
    }

    public static Optional<User> find(Integer userId) {
        UserDao dao = DaoFactory.createUserDao();
        return Optional.ofNullable(dao.findById(userId));
    }

    public static Optional<User> find(Email userEmail) {
        UserDao dao = DaoFactory.createUserDao();
        return dao.getUserByEmail(userEmail);
    }

    public static List<User> findAll() {
        UserDao dao = DaoFactory.createUserDao();
        return dao.findAll();
    }
}
