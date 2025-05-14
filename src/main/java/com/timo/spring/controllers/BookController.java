package com.timo.spring.controllers;

import com.timo.spring.dao.BookDAO;
import com.timo.spring.models.Book;
import com.timo.spring.util.BookValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDAO bookDAO;
    private final BookValidator bookValidator;

    @Autowired
    public BookController(BookDAO bookDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
    }

    @GetMapping
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookDAO.getAllBooks());
        return "books/all_books";
    }

    @GetMapping("/{id}")
    public String getBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.getBook(id).get());
        return "books/show_book";
    }

    @GetMapping("/new_book")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new_book";
    }

    @PostMapping
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "books/new_book";
        }

        bookDAO.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.getBook(id).get());
        return "books/edit_book";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") int id, @ModelAttribute @Valid Book book, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "books/edit_book";
        }

        bookDAO.updateBook(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }
}
