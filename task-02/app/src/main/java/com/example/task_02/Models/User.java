package com.example.task_02.Models;

public class User {

    private long id;

    private String firstName;

    private String lastName;

    private Phone phones;

    private String gender;

    private int photo;

    public User(){
    }

    public User(long id, String firstName, String lastName, Phone phones, String gender, int photo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phones = phones;
        this.gender = gender;
        this.photo = photo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Phone getPhones() {
        return phones;
    }

    public void setPhones(Phone phones) {
        this.phones = phones;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
