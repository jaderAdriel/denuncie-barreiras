package com.barreirasapp.entities;

import com.barreirasapp.entities.enums.Gender;
import com.barreirasapp.entities.valueobjects.Email;
import com.barreirasapp.entities.enums.UserRole;

import java.time.LocalDate;

public class User {
    private Integer id;
    private String name;
    private Email email;
    private LocalDate birthDate;
    private Gender gender;
    private String password;
    private UserRole userRole;

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

    public User(Integer id, String name, Email email, LocalDate birthDate, Gender gender, String password, UserRole userRole) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.gender = gender;
        this.password = password;
        this.userRole = userRole;
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

    public UserRole getUserRole() {
        return userRole;
    }
}
