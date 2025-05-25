package com.timo.spring.controllers;

import com.timo.spring.models.Book;
import com.timo.spring.services.BookService;
import com.timo.spring.services.PeopleService;
import com.timo.spring.util.BookValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final PeopleService peopleService;
    private final BookValidator bookValidator;

    @Autowired
    public BookController(BookService bookService, PeopleService peopleService, BookValidator bookValidator) {
        this.bookService = bookService;
        this.peopleService = peopleService;
        this.bookValidator = bookValidator;
    }

    @GetMapping
    public String getAllBooks(Model model,
                              @RequestParam(value = "page", required = false) Integer page,
                              @RequestParam(value = "size", required = false) Integer size,
                              @RequestParam(value = "sort_by_year", required = false) Boolean sortByYear) {
        if (page == null && size == null) {
            if (sortByYear != null && !sortByYear) {
                model.addAttribute("books", bookService.getAllBooks());
            } else {
                model.addAttribute("books", bookService.getAllBooksSortByYear());
            }
        } else if (page != null && size != null){
            if (sortByYear != null && !sortByYear) {
                model.addAttribute("books", bookService.getAllBooks(page, size));
            } else {
                model.addAttribute("books", bookService.getAllBooksSortByYear(page, size));
            }
        }

        return "books/all_books";
    }

    @GetMapping("/{id}")
    public String getBook(@PathVariable("id") int id,
                          Model model) {
        Book book = bookService.getBookById(id).get();
        model.addAttribute("book", book);
        if (book.getOwner() == null) {
            model.addAttribute("people", peopleService.getAllPeople());
        } else {
            model.addAttribute("person", peopleService.getPerson(book.getOwner().getId()).get());
        }

        return "books/show_book";
    }

    @PatchMapping("/{id}/set_owner")
    public String setOwner(@PathVariable("id") int bookId,
                           @RequestParam("personId") Integer personId) {
        bookService.setOwner(bookId, personId);
        return "redirect:/books/" + bookId;
    }

    @PatchMapping("/{id}/delete_owner")
    public String deleteOwner(@PathVariable("id") int bookId) {
        bookService.setOwner(bookId, null);
        return "redirect:/books/" + bookId;
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

        bookService.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.getBookById(id).get());
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

        bookService.updateBook(id, book);
        model.addAttribute("book", bookService.getBookById(id).get());
        return "books/show_book";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String searchBook(@RequestParam(value = "input", required = false) String input,
                             Model model) {

        if (input != null) {
            List<Book> books = bookService.getBookByTitleLike("%" + input + "%");
            model.addAttribute("books", books);
        }

        return "books/search_book";
    }

}
