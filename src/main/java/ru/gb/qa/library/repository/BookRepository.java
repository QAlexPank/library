package ru.gb.qa.library.repository;

import ru.gb.qa.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
