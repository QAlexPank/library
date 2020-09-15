package ru.gb.qa.library;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.gb.qa.library.model.Author;
import ru.gb.qa.library.model.Book;
import ru.gb.qa.library.model.Reader;
import ru.gb.qa.library.service.AuthorService;
import ru.gb.qa.library.service.BookService;
import ru.gb.qa.library.service.ReaderService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class LibraryServiceTest {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;

    @Autowired
    private ReaderService readerService;
    private List< Author > authorList;
    private List< Reader > readerList;
    private List< Book > bookList;

    @BeforeAll
    public void sourceData()
    {
        readerList = new ArrayList<>();
        readerList.add(new Reader("Петров"));
        readerList.add(new Reader("Васечкин"));
        readerList.add(new Reader("Старцева"));
        readerList = readerList
                .stream()
                .peek(readerService::save)
                .collect(Collectors.toList());

        authorList = new ArrayList<>();
        authorList.add(new Author("А.С.Пушкин"));
        authorList.add(new Author("Л.Н.Толстой"));
        authorList.add(new Author("Н.В.Гоголь"));
        authorList = authorList
                .stream()
                .peek(authorService::save)
                .collect(Collectors.toList());

        bookList = new ArrayList<>();
        bookList.add(new Book("Евгений Онегин", authorList.get(0), readerList.get(2)));
        bookList.add(new Book("Капитанская дочка", authorList.get(0), readerList.get(0)));
        bookList.add(new Book("Война и мир", authorList.get(1), readerList.get(2)));
        bookList.add(new Book("Анна Каренина", authorList.get(1), readerList.get(1)));
        bookList.add(new Book("Кавказский пленник", authorList.get(1), readerList.get(2)));
        bookList.add(new Book("Вий", authorList.get(2), readerList.get(0)));
        bookList.add(new Book("Мертвые души", authorList.get(2), readerList.get(2)));
        bookList = bookList
                .stream()
                .peek(bookService::save)
                .collect(Collectors.toList());
    }

    @DisplayName("Проверка на получение книг, которые читает читатель")
    @Test
    public void BookByReaderIdTest(){
        Long id = readerList.get(2).getId();
        List< Book > books = readerService.getBookByReaderId(id);
        Assertions.assertThat(books).hasSize(4);
        Assertions.assertThat(books.stream().map(Book::getName).collect(Collectors.toList())).contains("Евгений Онегин","Война и мир", "Кавказский пленник","Мертвые души");

    }

    @DisplayName("Проверка на получение книги автора")
    @Test
    public void BookByAuthorIdTest(){
        Long id = authorList.get(1).getId();
        List< Book > books = authorService.getBookByAuthorId(id);
        Assertions.assertThat(books).hasSize(3);
    }

    @DisplayName("Проверка на получение читателя, который читает книгу")
    @Test
    public void ReaderByBookIdTest(){
        Long id = bookList.get(3).getId();
        Reader reader = bookService.getReaderByBookId(id);
        Assertions.assertThat(reader.getName()).isEqualTo("Васечкин");
    }

    @DisplayName("Проверка на получение читателей, которые читают книги автора")
    @Test
    public void ReaderByAuthorIdTest(){
        Long id = authorList.get(2).getId();
        List< Reader > readerList = authorService.getReaderByAuthorId(id);
        Assertions.assertThat(readerList).hasSize(2);
    }
}


