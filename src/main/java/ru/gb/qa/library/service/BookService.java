package ru.gb.qa.library.service;

import org.springframework.stereotype.Service;
import ru.gb.qa.library.model.Book;
import ru.gb.qa.library.model.Reader;
import ru.gb.qa.library.repository.BookRepository;

import java.util.Optional;

@Service
public class BookService {

    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(Book book){
        return bookRepository.save(book);
    }

    public Reader getReaderByBookId(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get().getReader();
        } else {
            return null;
        }
    }
}
