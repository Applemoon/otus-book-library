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

    @Test
    void shouldFindBookById() {
        Book expectedBook = new Book();
        expectedBook.setTitle(SIA_TITLE);
        expectedBook.setGenreId(0);
        expectedBook.setAuthorId(0);
        given(dao.findById(10)).willReturn(expectedBook);

        Book actualBook = service.findById(10);

        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @Test
    void shouldUpdateBook() {
        Book actualBook = new Book();
        actualBook.setId(10);
        actualBook.setTitle(SIA_TITLE);
        actualBook.setAuthorId(0);
        actualBook.setGenreId(0);

        service.update(actualBook.getId(), actualBook.getTitle(), actualBook.getAuthorId(), actualBook.getGenreId());

        then(dao).should(times(1)).update(actualBook);
    }

    @Test
    void shouldNotUpdateBookWithEmptyTitle() {
        service.update(10, "", 0, 0);

        then(dao).shouldHaveNoInteractions();
    }

    @Test
    void shouldDeleteById() {
        service.deleteById(10);

        then(dao).should(times(1)).deleteById(10);
    }
}