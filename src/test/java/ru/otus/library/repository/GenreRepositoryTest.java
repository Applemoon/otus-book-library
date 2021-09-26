package ru.otus.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Genre's repository")
@DataJpaTest
@Import(GenreRepository.class)
class GenreRepositoryTest {

    private static final String TEST_GENRE_NAME = "genre";
    private static final String NOVEL_NAME = "novel";
    private static final long NOVEL_ID = 1;

    @Autowired
    private GenreRepository repository;

    @Autowired
    private TestEntityManager em;

    @Test
    void shouldFindByName() {
        Genre actualGenre = repository.findByName(NOVEL_NAME);

        Genre expectedGenre = em.find(Genre.class, NOVEL_ID);
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    void shouldNotFindByWrongName() {
        Genre actualGenre = repository.findByName(TEST_GENRE_NAME);

        assertThat(actualGenre).isNull();
    }

    @Test
    void shouldCreateGenre() {
        Genre expectedGenre = new Genre();
        expectedGenre.setName(TEST_GENRE_NAME);

        repository.create(expectedGenre);

        Genre actualGenre = em.find(Genre.class, 3L);
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @Test
    void shouldNotCreateGenreIfNull() {
        repository.create(null);

        Genre actualGenre = repository.findByName(TEST_GENRE_NAME);
        assertThat(actualGenre).isNull();
    }

    @Test
    void shouldFindAllGenres() {
        List<Genre> actualGenres = repository.findAll();

        List<Genre> expectedAuthors = em.getEntityManager()
                .createQuery("select g from Genre g", Genre.class)
                .getResultList();
        assertThat(actualGenres).usingRecursiveComparison().isEqualTo(expectedAuthors);
    }
}