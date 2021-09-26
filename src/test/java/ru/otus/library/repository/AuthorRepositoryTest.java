package ru.otus.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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
    private static final long PUSHKIN_ID = 1;

    @Autowired
    private AuthorRepository repository;

    @Autowired
    private TestEntityManager em;

    @Test
    void shouldFindByName() {
        Author actualAuthor = repository.findByName(PUSHKIN_NAME);

        Author expectedAuthor = em.find(Author.class, PUSHKIN_ID);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
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

        Author actualAuthor = em.find(Author.class, 3L);
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

        List<Author> expectedAuthors = em.getEntityManager()
                .createQuery("select a from Author a", Author.class)
                .getResultList();
        assertThat(actualAuthors).usingRecursiveComparison().isEqualTo(expectedAuthors);
    }
}