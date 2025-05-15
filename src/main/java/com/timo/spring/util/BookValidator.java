package com.timo.spring.util;

import com.timo.spring.dao.BookDAO;
import com.timo.spring.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class BookValidator implements Validator {

    private final BookDAO bookDAO;

    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        Optional<Book> bookOptional = bookDAO.getBookByTitleAndAuthor(book.getTitle(), book.getAuthor());

        if (bookOptional.isPresent() && book.equals(bookOptional.get())) {
            System.out.printf("Trying to add book that already exists - %s\n", bookOptional.get());
            errors.rejectValue("title", "", "Book with same author and title already exists");
        }
    }

    public void validate(String method, Object target, Errors errors) {
        Book book = (Book) target;
        Optional<Book> bookOptional = bookDAO.getBookByTitleAndAuthor(book.getTitle(), book.getAuthor());

        switch (method) {
            case "POST":
                if (bookOptional.isPresent() && book.equals(bookOptional.get())) {
                    System.out.printf("Trying to add book that already exists - %s\n", bookOptional.get());
                    errors.rejectValue("title", "", "Book with same author and title already exists");
                }
                break;
            case "PATCH":
                if (bookOptional.isPresent() && !book.equals(bookOptional.get())) {
                    System.out.printf("Trying to update book with values that already exists - %s\n", bookOptional.get());
                    errors.rejectValue("title", "", "Book with same author and title already exists");
                }
                break;
            default:
                errors.rejectValue("title", "", "Some unexpected error");
        }
    }
}
