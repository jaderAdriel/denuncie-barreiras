package com.barreirasapp.model.entities;

import com.barreirasapp.model.enums.Gender;
import com.barreirasapp.model.entities.valueobjects.Email;

import java.time.LocalDate;

public class Person {
    private Long id;
    private String name;
    private Email email;
    private LocalDate brithDate;
    private Gender gender;
}
