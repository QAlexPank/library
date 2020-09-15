package ru.gb.qa.library.repository;

import ru.gb.qa.library.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
}
