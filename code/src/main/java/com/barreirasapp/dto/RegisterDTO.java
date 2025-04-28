package com.barreirasapp.dto;

import com.barreirasapp.model.entities.valueobjects.Email;
import com.barreirasapp.model.enums.Gender;

import java.time.LocalDate;

public class RegisterDTO {
    public String name;
    public String email;
    public String birthDate;
    public String gender;
    public String password;
}
