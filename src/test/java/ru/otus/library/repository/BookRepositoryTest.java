package ru.otus.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Book's repository")
@DataJpaTest
@Import(BookRepository.class)
class BookRepositoryTest {

    private static final String TEST_BOOK_TITLE = "The Book";
    private static final String EUGENE_ONEGIN_TITLE = "Eugene Onegin";

    @Autowired
    private BookRepository repository;

    @Test
    void shouldCreateBook() {
        Book expectedBook = new Book();
        expectedBook.setTitle(TEST_BOOK_TITLE);
//        expectedBook.setAuthorId(0);
//        expectedBook.setGenreId(0);

        repository.create(expectedBook);

        expectedBook.setId(2);
        List<Book> actualBooks = repository.findAll();
        assertThat(actualBooks).contains(expectedBook);
    }

    @Test
    void shouldNotCreateBookIfNull() {
        final int firstSize = repository.findAll().size();

        repository.create(null);

        final int secondSize = repository.findAll().size();
        assertThat(secondSize).isEqualTo(firstSize);
    }

    @Test
    void shouldFindAllBooks() {
        List<Book> actualBooks = repository.findAll();

        Book expectedBook = new Book();
        expectedBook.setId(1);
        expectedBook.setTitle(EUGENE_ONEGIN_TITLE);
//        expectedBook.setAuthorId(0);
//        expectedBook.setGenreId(0);
        assertThat(actualBooks).containsExactly(expectedBook);
    }

    @Test
    void shouldFindBookByTitle() {
        Book actualBook = repository.findByTitle(EUGENE_ONEGIN_TITLE);

        Book expectedBook = new Book();
        expectedBook.setId(actualBook.getId());
        expectedBook.setTitle(EUGENE_ONEGIN_TITLE);
//        expectedBook.setAuthorId(0);
//        expectedBook.setGenreId(0);
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @Test
    void shouldNotFindBookByWrongTitle() {
        Book actualBook = repository.findByTitle(TEST_BOOK_TITLE);

        assertThat(actualBook).isNull();
    }

    @Test
    void shouldFindBookById() {
        Book actualBook = repository.findById(1);

        Book expectedBook = new Book();
        expectedBook.setId(1);
        expectedBook.setTitle(EUGENE_ONEGIN_TITLE);
//        expectedBook.setAuthorId(0);
//        expectedBook.setGenreId(0);
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @Test
    void shouldNotFindBookByWrongId() {
        Book actualBook = repository.findById(100);

        assertThat(actualBook).isNull();
    }

    @Test
    void shouldUpdateBook() {
        Book expectedBook = new Book();
        expectedBook.setId(1);
        expectedBook.setTitle(TEST_BOOK_TITLE);
//        expectedBook.setAuthorId(1);
//        expectedBook.setGenreId(1);

        repository.updateTitleById(1, TEST_BOOK_TITLE);

        Book actualBook = repository.findById(expectedBook.getId());
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @Test
    void shouldDeleteBookById() {
        final int firstSize = repository.findAll().size();

        boolean deleted = repository.deleteById(1);

        assertThat(deleted).isTrue();
        final int secondSize = repository.findAll().size();
        assertThat(secondSize).isEqualTo(firstSize - 1);
    }
}