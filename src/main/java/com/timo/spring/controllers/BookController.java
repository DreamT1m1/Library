package com.timo.spring.controllers;

import com.timo.spring.dao.BookDAO;
import com.timo.spring.dao.PersonDAO;
import com.timo.spring.models.Book;
import com.timo.spring.models.Person;
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
    private final PersonDAO personDAO;
    private final BookValidator bookValidator;

    @Autowired
    public BookController(BookDAO bookDAO, BookValidator bookValidator, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
        this.personDAO = personDAO;
    }

    @GetMapping
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookDAO.getAllBooks());
        return "books/all_books";
    }

    @GetMapping("/{id}")
    public String getBook(@PathVariable("id") int id,
                          Model model) {
        Book book = bookDAO.getBook(id).get();
        model.addAttribute("book", book);
        if (book.getPerson_id() == null) {
            model.addAttribute("people", personDAO.getAllPeople());
        } else {
            model.addAttribute("person", personDAO.getPerson(book.getPerson_id()).get());
        }

        return "books/show_book";
    }

    @PatchMapping("/{id}/set_owner")
    public String setOwner(@PathVariable("id") int bookId,
                           @RequestParam("personId") Integer personId) {
        bookDAO.setOwner(bookId, personId);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/delete_owner")
    public String deleteOwner(@PathVariable("id") int bookId) {
        bookDAO.setOwner(bookId, null);
        return "redirect:/books";
    }

    @GetMapping("/new_book")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new_book";
    }

    @PostMapping
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate("POST", book, bindingResult);

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
    public String updateBook(@PathVariable("id") int id,
                             @ModelAttribute @Valid Book book,
                             BindingResult bindingResult,
                             Model model) {
        bookValidator.validate("PATCH", book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "books/edit_book";
        }

        bookDAO.updateBook(id, book);
        model.addAttribute("book", bookDAO.getBook(id).get());
        return "books/show_book";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }
}
