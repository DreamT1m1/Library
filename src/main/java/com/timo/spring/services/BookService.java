package com.timo.spring.services;

import com.timo.spring.models.Book;
import com.timo.spring.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Book::getId))
                .toList();
    }

    public List<Book> getAllBooks(int page, int size) {
        return bookRepository.findAll(PageRequest.of(page - 1, size)).getContent()
                .stream()
                .sorted(Comparator.comparing(Book::getId))
                .toList();
    }

    public List<Book> getAllBooksSortByYear() {
        return bookRepository.findAll(Sort.by("year"));
    }

    public List<Book> getAllBooksSortByYear(int page, int size) {
        return bookRepository.findAll(PageRequest.of(page - 1, size, Sort.by("year"))).getContent();
    }

    public Optional<Book> getBookById(int id) {
        return bookRepository.findById(id);
    }

    public Optional<Book> getBookByTitleAndAuthor(String title, String author) {
        return bookRepository.findByTitleAndAuthor(title, author);
    }

    public List<Book> getBookByTitleLike(String title) {
        return bookRepository.findByTitleLikeIgnoreCase(title);
    }

    @Transactional
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void updateBook(int id, Book book) {
        book.setId(id);
        bookRepository.save(book);
    }

    @Transactional
    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void setOwner(int bookId, Integer ownerId) {
        bookRepository.setOwner(bookId, ownerId);
    }
}
