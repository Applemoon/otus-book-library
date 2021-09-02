package ru.otus.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Book's dao")
@JdbcTest
@Import(BookDao.class)
class BookDaoTest {

    private static final String TEST_BOOK_TITLE = "The Book";
    private static final String EUGENE_ONEGIN_TITLE = "Eugene Onegin";

    @Autowired
    private BookDao dao;

    @Test
    void shouldCreateBook() {
        Book expectedBook = new Book();
        expectedBook.setTitle(TEST_BOOK_TITLE);
        expectedBook.setAuthorId(0);
        expectedBook.setGenreId(0);

        dao.create(expectedBook);

        expectedBook.setId(2);
        List<Book> actualBooks = dao.findAll();
        assertThat(actualBooks).contains(expectedBook);
    }

    @Test
    void shouldNotCreateBookIfNull() {
        final int firstSize = dao.findAll().size();

        dao.create(null);

        final int secondSize = dao.findAll().size();
        assertThat(secondSize).isEqualTo(firstSize);
    }

    @Test
    void shouldFindAllBooks() {
        List<Book> actualBooks = dao.findAll();

        Book expectedBook = new Book();
        expectedBook.setId(1);
        expectedBook.setTitle(EUGENE_ONEGIN_TITLE);
        expectedBook.setAuthorId(0);
        expectedBook.setGenreId(0);
        assertThat(actualBooks).containsExactly(expectedBook);
    }

    @Test
    void shouldFindBookByTitle() {
        Book actualBook = dao.findByTitle(EUGENE_ONEGIN_TITLE);

        Book expectedBook = new Book();
        expectedBook.setId(actualBook.getId());
        expectedBook.setTitle(EUGENE_ONEGIN_TITLE);
        expectedBook.setAuthorId(0);
        expectedBook.setGenreId(0);
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @Test
    void shouldNotFindBookByWrongTitle() {
        Book actualBook = dao.findByTitle(TEST_BOOK_TITLE);

        assertThat(actualBook).isNull();
    }

    @Test
    void shouldFindBookById() {
        Book actualBook = dao.findById(1);

        Book expectedBook = new Book();
        expectedBook.setId(1);
        expectedBook.setTitle(EUGENE_ONEGIN_TITLE);
        expectedBook.setAuthorId(0);
        expectedBook.setGenreId(0);
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @Test
    void shouldNotFindBookByWrongId() {
        Book actualBook = dao.findById(100);

        assertThat(actualBook).isNull();
    }

    @Test
    void shouldUpdateBook() {
        Book expectedBook = new Book();
        expectedBook.setId(1);
        expectedBook.setTitle(TEST_BOOK_TITLE);
        expectedBook.setAuthorId(1);
        expectedBook.setGenreId(1);

        dao.update(expectedBook);

        Book actualBook = dao.findById(expectedBook.getId());
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @Test
    void shouldDeleteBookById() {
        final int firstSize = dao.findAll().size();

        boolean deleted = dao.deleteById(1);

        assertThat(deleted).isTrue();
        final int secondSize = dao.findAll().size();
        assertThat(secondSize).isEqualTo(firstSize - 1);
    }
}