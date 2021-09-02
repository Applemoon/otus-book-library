package ru.otus.library.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.library.dao.AuthorDao;
import ru.otus.library.domain.Author;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@DisplayName("Author's service")
@SpringBootTest
class AuthorServiceTest {

    private static final String PALAHNIUK_NAME = "Palahniuk";

    @Autowired
    private AuthorService service;

    @MockBean
    private AuthorDao dao;

    @Test
    void shouldCreateAuthor() {
        service.create(PALAHNIUK_NAME);

        Author expectedAuthor = new Author();
        expectedAuthor.setName(PALAHNIUK_NAME);
        then(dao).should(times(1)).create(expectedAuthor);
    }

    @Test
    void shouldNotCreateAuthorWithoutName() {
        service.create(null);

        then(dao).shouldHaveNoInteractions();
    }

    @Test
    void shouldFindAuthorByName() {
        Author expectedAuthor = new Author();
        expectedAuthor.setName(PALAHNIUK_NAME);
        given(dao.findByName(PALAHNIUK_NAME)).willReturn(expectedAuthor);

        Author actualAuthor = service.findByName(PALAHNIUK_NAME);

        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @Test
    void shouldNotFindAuthorByWrongName() {
        given(dao.findByName(PALAHNIUK_NAME)).willReturn(null);

        Author actualAuthor = service.findByName(PALAHNIUK_NAME);

        assertThat(actualAuthor).isNull();
    }

    @Test
    void shouldFindAllAuthors() {
        Author expectedAuthor = new Author();
        expectedAuthor.setName(PALAHNIUK_NAME);
        expectedAuthor.setId(0);
        given(dao.findAll()).willReturn(Collections.singletonList(expectedAuthor));

        List<Author> actualAuthors = service.findAll();

        assertThat(actualAuthors).containsExactly(expectedAuthor);
        then(dao).should(times(1)).findAll();
    }

    @Test
    void shouldFindNoAuthorsIfEmpty() {
        given(dao.findAll()).willReturn(Collections.emptyList());

        List<Author> actualAuthors = service.findAll();

        assertThat(actualAuthors).isEmpty();
        then(dao).should(times(1)).findAll();
    }
}