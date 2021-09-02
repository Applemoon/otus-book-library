package ru.otus.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Author's dao")
@JdbcTest
@Import(AuthorDao.class)
class AuthorDaoTest {

    private static final String TEST_AUTHOR_NAME = "author";
    private static final String PUSHKIN_NAME = "Pushkin";

    @Autowired
    private AuthorDao dao;

    @Test
    void shouldFindByName() {
        Author actualAuthor = dao.findByName(PUSHKIN_NAME);

        assertThat(actualAuthor.getId()).isEqualTo(0);
        assertThat(actualAuthor.getName()).isEqualTo(PUSHKIN_NAME);
    }

    @Test
    void shouldNotFindByWrongName() {
        Author actualAuthor = dao.findByName(TEST_AUTHOR_NAME);

        assertThat(actualAuthor).isNull();
    }

    @Test
    void shouldCreateAuthor() {
        Author expectedAuthor = new Author();
        expectedAuthor.setName(TEST_AUTHOR_NAME);

        dao.create(expectedAuthor);

        Author actualAuthor = dao.findByName(TEST_AUTHOR_NAME);
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @Test
    void shouldNotCreateAuthorIfNull() {
        dao.create(null);

        Author actualAuthor = dao.findByName(TEST_AUTHOR_NAME);
        assertThat(actualAuthor).isNull();
    }

    @Test
    void shouldFindAllAuthors() {
        List<Author> actualAuthors = dao.findAll();

        Author author = new Author();
        author.setName(PUSHKIN_NAME);
        author.setId(0);
        assertThat(actualAuthors).containsExactly(author);
    }
}