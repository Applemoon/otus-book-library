package ru.otus.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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
    private static final String DISCOVERY_TITLE = "Discovery";
    private static final long EUGENE_ONEGIN_ID = 1;

    @Autowired
    private BookRepository repository;

    @Autowired
    private TestEntityManager em;

    @Test
    void shouldCreateBook() {
        Book expectedBook = new Book(3, TEST_BOOK_TITLE, 1L, 1L);

        repository.create(expectedBook);

        Book actualBook = em.find(Book.class, 3L);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    void shouldNotCreateBookIfNull() {
        final int firstSize = repository.findAll().size();

        repository.create(null);

        assertThat(em.getEntityManager()
                .createQuery("select count(*) from Book b", Long.class)
                .getSingleResult())
                .isEqualTo(firstSize);
    }

    @Test
    void shouldFindAllBooks() {
        List<Book> actualBooks = repository.findAll();

        Book eugeneOneginBook = new Book(1, EUGENE_ONEGIN_TITLE, 1L, 1L);
        Book discoveryBook = new Book(2, DISCOVERY_TITLE, 2L, 1L);
        assertThat(actualBooks).usingRecursiveComparison().isEqualTo(List.of(eugeneOneginBook, discoveryBook));
    }

    @Test
    void shouldFindBookByTitle() {
        Book actualBook = repository.findByTitle(EUGENE_ONEGIN_TITLE);
        Book expectedBook = em.find(Book.class, EUGENE_ONEGIN_ID);

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
        Book expectedBook = em.find(Book.class, EUGENE_ONEGIN_ID);

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
        expectedBook.setAuthorId(1L);
        expectedBook.setGenreId(1L);

        repository.updateById(1, TEST_BOOK_TITLE, 1, 1);

        Book actualBook = repository.findById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(actualBook);
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