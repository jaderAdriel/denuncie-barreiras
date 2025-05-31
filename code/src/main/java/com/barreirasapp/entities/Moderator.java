package com.barreirasapp.entities;

import com.barreirasapp.entities.valueobjects.Email;
import com.barreirasapp.entities.enums.Gender;

import java.time.LocalDate;

public class Moderator extends User {
    private Address address;
    private String cellphone;

    public Moderator(String password, Gender gender, LocalDate birthDate, Email email, String name, String cellphone) {
        super(password, email, birthDate, gender, name);
        this.cellphone = cellphone;
    }

    public void setAddress(String street, String city, String state, String postalCode) {
        this.address = new Address(street, city, state, postalCode);
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getAddress() {
        return this.address.getStreet() + ", " +
                this.address.getCity() + " - " +
                this.address.getState() + " " +
                this.address.getPostalCode();
    }

    public void setAddressStreet(String street) {
        this.address.setStreet(street);
    }

    public void setAddressCity(String city) {
        this.address.setCity(city);

    }

    public void setAddressState(String state) {
        this.address.setState(state);

    }


    public void setAddressPostalCode(String PostalCode) {
        this.address.setPostalCode(PostalCode);
    }

    public String getAddressStreet(String street) {
        return this.address.getStreet();
    }

    public String getAddressCity(String city) {
        return this.address.getCity();
    }

    public String getAddressState(String state) {
        return this.address.getState();
    }

    public String getAddressPostalCode(String PostalCode) {
        return this.address.getPostalCode();
    }
}
