package com.barreirasapp.entities;

import com.barreirasapp.entities.enums.EntityType;

import java.time.LocalDateTime;

public class Entity {
    private String cnpj;
    private String name;
    private Address address;
    private String phone;
    private LocalDateTime createAt;
    private EntityType type;

    public Entity(String cnpj, String name, Address address, String phone, LocalDateTime createAt, EntityType type) {
        this.cnpj = cnpj;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.createAt = createAt;
        this.type = type;
    }

    public Entity(String name, String cnpj,EntityType type, String phone) {
        this.cnpj = cnpj;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.type = type;
    }

    public Entity() {
    }

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressCity(){
        return address.getCity();
    }

    public String getAddressState(){
        return address.getState();
    }

    public String getAddressPostalCode(){
        return address.getPostalCode();
    }

    public String getAddressStreet(){
        return address.getStreet();
    }

    public String getAddress() {
        return this.address.getStreet() + ", " +
                this.address.getCity() + " - " +
                this.address.getState() + " " +
                this.address.getPostalCode();
    }

    public void setAddress(String street, String city, String state, String postalCode) {
        this.address = new Address(street, city, state, postalCode);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
}
