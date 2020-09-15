package ru.gb.qa.library.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.qa.library.model.Book;
import ru.gb.qa.library.model.Reader;
import ru.gb.qa.library.service.AuthorService;
import ru.gb.qa.library.service.BookService;
import ru.gb.qa.library.service.ReaderService;

import java.util.List;

@RestController
public class LibraryController {

    private AuthorService authorService;
    private BookService bookService;
    private ReaderService readerService;

    public LibraryController(AuthorService authorService, BookService bookService, ReaderService readerService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.readerService = readerService;
    }

    @GetMapping("/findBooksByReader/{id}")
    public List<Book> findBooksByReader(@PathVariable Long id) {
        List<Book> booksByReaderId = readerService.getBookByReaderId(id);
        return booksByReaderId;
    }

    @GetMapping("/findBooksByAuthor/{id}")
    public List<Book> findBooksByAuthor(@PathVariable Long id) {
        return authorService.getBookByAuthorId(id);
    }

    @GetMapping("/getReaderByBook/{id}")
    public Reader getReaderByBookId(@PathVariable Long id) {
        return bookService.getReaderByBookId(id);
    }

    @GetMapping("/getReadersByAuthor/{id}")
    public List<Reader> getReadersByAuthorId(@PathVariable Long id) {
        return authorService.getReaderByAuthorId(id);
    }

}
