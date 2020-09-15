package ru.gb.qa.library.service;

import org.springframework.stereotype.Service;
import ru.gb.qa.library.model.Book;
import ru.gb.qa.library.model.Reader;
import ru.gb.qa.library.repository.ReaderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReaderService {

    private ReaderRepository readerRepository;

    public ReaderService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public Reader save(Reader reader){
        return readerRepository.save(reader);
    }

    public List<Book> getBookByReaderId(Long id) {
        Optional<Reader> reader = readerRepository.findById(id);
        if (reader.isPresent()) {
            return reader.get().getBooks();
        } else {
            return new ArrayList<>();
        }
    }
}
