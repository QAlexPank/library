package ru.gb.qa.library.service;

import org.springframework.stereotype.Service;
import ru.gb.qa.library.model.Author;
import ru.gb.qa.library.model.Book;
import ru.gb.qa.library.model.Reader;
import ru.gb.qa.library.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private AuthorRepository authorRepository;
    private BookService bookService;

    public AuthorService(AuthorRepository authorRepository, BookService bookService) {
        this.authorRepository = authorRepository;
        this.bookService = bookService;
    }

    public List<Book> getBookByAuthorId(Long id){
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            return author.get().getBooks();
        } else {
            return new ArrayList<>();
        }
    }

    public Author save(Author author) {
        return authorRepository.save(author);
    }

    public List<Reader> getReaderByAuthorId(Long id){
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            List<Book> books = author.get().getBooks();
            if (books != null) {
                return books.stream()
                        .map( it -> bookService.getReaderByBookId(it.getId()))
                        .filter( it -> it != null)
                        .distinct()
                        .collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
    }
}
