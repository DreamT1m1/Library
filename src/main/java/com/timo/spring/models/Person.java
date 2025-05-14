package com.timo.spring.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Person {

    private int id;

    @NotEmpty(message = "Name field cannot be empty!")
    @Size(min = 5, max = 50, message =  "Entered name must be valid size")
    private String fullName;

    @NotEmpty(message = "Birth year field cannot be empty!")
    private int birthYear;

    public Person(int id, String fullName, int birthYear) {
        this.id = id;
        this.fullName = fullName;
        this.birthYear = birthYear;
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
}
