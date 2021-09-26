package ru.otus.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Author's repository")
@DataJpaTest
@Import(AuthorRepository.class)
class AuthorRepositoryTest {

    private static final String TEST_AUTHOR_NAME = "author";
    private static final String PUSHKIN_NAME = "Pushkin";
    private static final String BRODSKY_NAME = "Brodsky";

    @Autowired
    private AuthorRepository repository;

    @Test
    void shouldFindByName() {
//        Author actualAuthor = repository.findByName(PUSHKIN_NAME);
//
//        assertThat(actualAuthor.getName()).isEqualTo(PUSHKIN_NAME);
    }

    @Test
    void shouldNotFindByWrongName() {
        Author actualAuthor = repository.findByName(TEST_AUTHOR_NAME);

        assertThat(actualAuthor).isNull();
    }

    @Test
    void shouldCreateAuthor() {
        Author expectedAuthor = new Author();
        expectedAuthor.setName(TEST_AUTHOR_NAME);

        repository.create(expectedAuthor);

        Author actualAuthor = repository.findByName(TEST_AUTHOR_NAME);
        expectedAuthor.setId(actualAuthor.getId());
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @Test
    void shouldNotCreateAuthorIfNull() {
        repository.create(null);

        Author actualAuthor = repository.findByName(TEST_AUTHOR_NAME);
        assertThat(actualAuthor).isNull();
    }

    @Test
    void shouldFindAllAuthors() {
        List<Author> actualAuthors = repository.findAll();

        Author pushkin = new Author();
        pushkin.setId(1);
        pushkin.setName(PUSHKIN_NAME);

        Author brodsky = new Author();
        brodsky.setId(2);
        brodsky.setName(BRODSKY_NAME);
        assertThat(actualAuthors).containsExactly(pushkin, brodsky);
    }
}