package ru.otus.library.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.library.dao.BookDao;
import ru.otus.library.domain.Book;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@DisplayName("Book's service")
@SpringBootTest
class BookServiceTest {

    private static final String SIA_TITLE = "Spring in action";

    @Autowired
    private BookService service;

    @MockBean
    private BookDao dao;

    @Test
    void shouldCreateBook() {
        service.create(SIA_TITLE, 0, 0);

        Book expectedBook = new Book();
        expectedBook.setTitle(SIA_TITLE);
        then(dao).should(times(1)).create(expectedBook);
    }

    @Test
    void shouldNotCreateBookWithoutName() {
        service.create(null, 0, 0);

        then(dao).shouldHaveNoInteractions();
    }

    @Test
    void shouldFindAllBooks() {
        Book expectedBook = new Book();
        expectedBook.setTitle(SIA_TITLE);
        expectedBook.setGenreId(0);
        expectedBook.setAuthorId(0);
        given(dao.findAll()).willReturn(Collections.singletonList(expectedBook));

        List<Book> actualBooks = service.findAll();

        assertThat(actualBooks).containsExactly(expectedBook);
        then(dao).should(times(1)).findAll();
    }

    @Test
    void shouldFindNoBooksIfEmpty() {
        given(dao.findAll()).willReturn(Collections.emptyList());

        List<Book> actualBooks = service.findAll();

        assertThat(actualBooks).isEmpty();
        then(dao).should(times(1)).findAll();
    }
}