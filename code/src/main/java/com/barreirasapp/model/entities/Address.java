package com.barreirasapp.model.entities;

public class Address {
    private String street;
    private String city;
    private String state;
    private String postalCode;

    public Address(String street, String city, String state, String postalCode) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }

    protected String getStreet() {
        return street;
    }

    protected void setStreet(String street) {
        this.street = street;
    }

    protected String getCity() {
        return city;
    }

    protected void setCity(String city) {
        this.city = city;
    }

    protected String getState() {
        return state;
    }

    protected void setState(String state) {
        this.state = state;
    }

    protected String getPostalCode() {
        return postalCode;
    }

    protected void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
