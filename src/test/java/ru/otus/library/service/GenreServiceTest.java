package ru.otus.library.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.library.dao.GenreDao;
import ru.otus.library.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@DisplayName("Genre's service")
@SpringBootTest
class GenreServiceTest {

    private static final String COMEDY_NAME = "comedy";

    @Autowired
    private GenreService service;

    @MockBean
    private GenreDao dao;

    @Test
    void shouldCreateGenre() {
        service.create(COMEDY_NAME);

        Genre expectedGenre = new Genre();
        expectedGenre.setName(COMEDY_NAME);
        then(dao).should(times(1)).create(expectedGenre);
    }

    @Test
    void shouldNotCreateGenreWithoutName() {
        service.create(null);

        then(dao).shouldHaveNoInteractions();
    }

    @Test
    void shouldFindGenreByName() {
        Genre expectedGenre = new Genre();
        expectedGenre.setName(COMEDY_NAME);
        given(dao.findByName(COMEDY_NAME)).willReturn(expectedGenre);

        Genre actualGenre = service.findByName(COMEDY_NAME);

        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @Test
    void shouldNotFindGenreByWrongName() {
        given(dao.findByName(COMEDY_NAME)).willReturn(null);

        Genre actualGenre = service.findByName(COMEDY_NAME);

        assertThat(actualGenre).isNull();
    }
}