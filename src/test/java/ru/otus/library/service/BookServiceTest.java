package ru.otus.library.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.domain.Book;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
    private BookRepository repository;

    @Test
    void shouldCreateBook() {
        service.create(SIA_TITLE);

        then(repository).should(times(1)).create(any(Book.class));
    }

    @Test
    void shouldNotCreateBookWithoutName() {
        service.create(null);

        then(repository).shouldHaveNoInteractions();
    }

    @Test
    void shouldFindAllBooks() {
        Book expectedBook = new Book();
        expectedBook.setTitle(SIA_TITLE);
//        expectedBook.setGenreId(0);
//        expectedBook.setAuthorId(0);
        given(repository.findAll()).willReturn(Collections.singletonList(expectedBook));

        List<Book> actualBooks = service.findAll();

        assertThat(actualBooks).containsExactly(expectedBook);
        then(repository).should(times(1)).findAll();
    }

    @Test
    void shouldFindNoBooksIfEmpty() {
        given(repository.findAll()).willReturn(Collections.emptyList());

        List<Book> actualBooks = service.findAll();

        assertThat(actualBooks).isEmpty();
        then(repository).should(times(1)).findAll();
    }

    @Test
    void shouldFindBookById() {
        Book expectedBook = new Book();
        expectedBook.setTitle(SIA_TITLE);
//        expectedBook.setGenreId(0);
//        expectedBook.setAuthorId(0);
        given(repository.findById(10)).willReturn(expectedBook);

        Book actualBook = service.findById(10);

        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @Test
    void shouldUpdateBook() {
        Book actualBook = new Book();
        actualBook.setId(10);
        actualBook.setTitle(SIA_TITLE);
//        actualBook.setAuthorId(0);
//        actualBook.setGenreId(0);

        service.update(actualBook.getId(), actualBook.getTitle());

        then(repository).should(times(1)).updateTitleById(10, SIA_TITLE);
    }

    @Test
    void shouldNotUpdateBookWithEmptyTitle() {
        service.update(10, "");

        then(repository).shouldHaveNoInteractions();
    }

    @Test
    void shouldDeleteById() {
        service.deleteById(10);

        then(repository).should(times(1)).deleteById(10);
    }
}