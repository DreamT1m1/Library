package com.timo.spring.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Value cannot be empty")
    @Size(min = 3, max = 70, message = "Entered title must be valid size!")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Value cannot be empty")
    @Size(min = 3, max = 50, message = "Entered name must be valid size!")
    @Column(name = "author")
    private String author;

    @NotNull(message = "Value cannot be empty")
    @Column(name = "year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @NotEmpty(message = "Value cannot be empty")
    @Size(min = 10, max = 3000, message = "Description must be at least 10 and no longer than 3000 symbols")
    @Column(name = "description")
    private String description;

    public Book(int id, Person owner, String title, String author, int year, String description) {
        this.id = id;
        this.owner = owner;
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

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
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

    @Override
    public String toString() {
        return String.format("%s - %s (%d)",
                getAuthor(),
                getTitle(),
                getYear());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Book)) {
            return false;
        }
        Book book = (Book) obj;
        return getAuthor().equals(book.getAuthor()) && getTitle().equals(book.getTitle());
    }
}
