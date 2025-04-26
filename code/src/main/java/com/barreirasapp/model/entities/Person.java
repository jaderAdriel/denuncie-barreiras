package com.barreirasapp.model.entities;

import com.barreirasapp.model.dao.DaoFactory;
import com.barreirasapp.model.dao.PersonDao;
import com.barreirasapp.model.enums.Gender;
import com.barreirasapp.model.entities.valueobjects.Email;

import java.time.LocalDate;

public class Person {
    private Integer id;
    private String name;
    private Email email;
    private LocalDate birthDate;
    private Gender gender;

    public Person(String name, Email email, LocalDate birthDate, Gender gender) {
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public Person() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setEmail(Email email) {
        this.email = email;
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

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void save() {
        PersonDao dao = DaoFactory.createPersonDao();
        dao.insert(this);
    }
}
