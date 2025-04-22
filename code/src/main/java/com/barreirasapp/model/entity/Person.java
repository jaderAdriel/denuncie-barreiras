package com.barreirasapp.model.entity;

import com.barreirasapp.model.enums.GeneroEnum;
import com.barreirasapp.model.valueobjects.Email;

import java.time.LocalDate;

public class Person {
    private Long id;
    private String nome;
    private Email email;
    private LocalDate dataNascimento;
    private GeneroEnum genero;
}
