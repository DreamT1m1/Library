package com.timo.spring.dao;

import com.timo.spring.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAllBooks() {
        return jdbcTemplate.query(
                "SELECT * FROM book ORDER BY id",
                new BeanPropertyRowMapper<>(Book.class)
        );
    }

    public Optional<Book> getBook(int id) {
        return jdbcTemplate.query(
                "SELECT * FROM book WHERE id=?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)
        ).stream().findAny();
    }

    public Optional<Book> getBookByTitleAndAuthor(String title, String author) {
        return jdbcTemplate.query(
                "SELECT * FROM book WHERE title=? AND author=?",
                new Object[]{title, author},
                new BeanPropertyRowMapper<>(Book.class)
        ).stream().findAny();
    }

    public void addBook(Book book) {
        jdbcTemplate.update(
                "INSERT INTO book(title, author, year, description) VALUES (?, ?, ?, ?)",
                book.getTitle(),
                book.getAuthor(),
                book.getYear(),
                book.getDescription()
        );
    }

    public void updateBook(int id, Book book) {
        jdbcTemplate.update("UPDATE book SET title=?, author=?, year=?, description=? WHERE id=?",
                book.getTitle(),
                book.getAuthor(),
                book.getYear(),
                book.getDescription(),
                id);
    }

    public void deleteBook(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id=?",
                id);
    }

    public void setOwner(int bookId, Integer ownerId) {
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE id=?",
                ownerId,
                bookId);
    }
}
