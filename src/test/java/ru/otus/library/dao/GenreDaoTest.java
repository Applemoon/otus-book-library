package ru.otus.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Genre's dao")
@JdbcTest
@Import(GenreDao.class)
class GenreDaoTest {

    private static final String TEST_GENRE_NAME = "genre";
    private static final String NOVEL_NAME = "novel";

    @Autowired
    private GenreDao dao;

    @Test
    void shouldFindByName() {
        Genre actualGenre = dao.findByName(NOVEL_NAME);

        assertThat(actualGenre.getId()).isEqualTo(0);
        assertThat(actualGenre.getName()).isEqualTo(NOVEL_NAME);
    }

    @Test
    void shouldNotFindByWrongName() {
        Genre actualGenre = dao.findByName(TEST_GENRE_NAME);

        assertThat(actualGenre).isNull();
    }

    @Test
    void shouldCreateGenre() {
        Genre expectedGenre = new Genre();
        expectedGenre.setName(TEST_GENRE_NAME);

        dao.create(expectedGenre);

        Genre actualGenre = dao.findByName(TEST_GENRE_NAME);
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @Test
    void shouldNotCreateGenreIfNull() {
        dao.create(null);

        Genre actualGenre = dao.findByName(TEST_GENRE_NAME);
        assertThat(actualGenre).isNull();
    }

    @Test
    void shouldFindAllGenres() {
        List<Genre> actualGenres = dao.findAll();

        Genre genre = new Genre();
        genre.setName(NOVEL_NAME);
        genre.setId(0);
        assertThat(actualGenres).containsExactly(genre);
    }
}