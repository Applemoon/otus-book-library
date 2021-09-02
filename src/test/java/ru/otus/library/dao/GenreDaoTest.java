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
    private static final String FANTASY_NAME = "fantasy";

    @Autowired
    private GenreDao dao;

    @Test
    void shouldFindByName() {
        Genre actualGenre = dao.findByName(NOVEL_NAME);

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
        expectedGenre.setId(actualGenre.getId());
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

        Genre novel = new Genre();
        novel.setId(1);
        novel.setName(NOVEL_NAME);

        Genre fantasy = new Genre();
        fantasy.setId(2);
        fantasy.setName(FANTASY_NAME);
        assertThat(actualGenres).containsExactly(novel, fantasy);
    }
}