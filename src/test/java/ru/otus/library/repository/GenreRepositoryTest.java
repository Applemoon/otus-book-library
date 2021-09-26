package ru.otus.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
    private static final String FANTASY_NAME = "fantasy";

    @Autowired
    private GenreRepository repository;

    @Test
    void shouldFindByName() {
        Genre actualGenre = repository.findByName(NOVEL_NAME);

        assertThat(actualGenre.getName()).isEqualTo(NOVEL_NAME);
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

        Genre actualGenre = repository.findByName(TEST_GENRE_NAME);
        expectedGenre.setId(actualGenre.getId());
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

        Genre novel = new Genre();
        novel.setId(1);
        novel.setName(NOVEL_NAME);

        Genre fantasy = new Genre();
        fantasy.setId(2);
        fantasy.setName(FANTASY_NAME);
        assertThat(actualGenres).containsExactly(novel, fantasy);
    }
}