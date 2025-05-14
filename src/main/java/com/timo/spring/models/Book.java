package com.timo.spring.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Book {

    private int id;
    private Integer user_id;

    @NotEmpty
    @Size(min = 3, max = 70, message = "Entered title must be valid size!")
    private String title;
    @NotEmpty
    @Size(min = 3, max = 50, message = "Entered name must be valid size!")
    private String author;
    @NotNull
    private int year;
    @NotEmpty
    @Size(min = 10, max = 3000, message = "Description must be at least 10 and no longer than 3000 symbols")
    private String description;

    public Book(int id, Integer user_id, String title, String author, int year, String description) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.description = description;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
