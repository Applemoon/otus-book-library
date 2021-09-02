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

        Book book = new Book();
        book.setTitle(EUGENE_ONEGIN_TITLE);
        book.setAuthorId(0);
        book.setGenreId(0);
        assertThat(actualBooks).containsExactly(book);
    }
}