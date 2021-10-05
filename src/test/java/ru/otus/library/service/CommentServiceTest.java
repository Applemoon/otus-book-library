package ru.otus.library.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.library.domain.Comment;
import ru.otus.library.repository.CommentRepository;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@DisplayName("Comment's service")
@SpringBootTest
class CommentServiceTest {

    private static final String TEST_TEXT = "test text";

    @Autowired
    private CommentService service;

    @MockBean
    private CommentRepository repository;

    @Test
    void shouldCreateComment() {
        service.create(TEST_TEXT);

        then(repository).should(times(1)).save(any(Comment.class));
    }

    @Test
    void shouldNotCreateCommentWithoutText() {
        service.create(null);

        then(repository).shouldHaveNoInteractions();
    }

    @Test
    void shouldFindCommentByText() {
        Comment expectedComment = new Comment();
        expectedComment.setText(TEST_TEXT);
        given(repository.findByText(TEST_TEXT)).willReturn(expectedComment);

        Comment actualComment = service.findByText(TEST_TEXT);

        assertThat(actualComment).isEqualTo(expectedComment);
    }

    @Test
    void shouldNotFindCommentByWrongText() {
        given(repository.findByText(TEST_TEXT)).willReturn(null);

        Comment actualComment = service.findByText(TEST_TEXT);

        assertThat(actualComment).isNull();
    }

    @Test
    void shouldFindAllComments() {
        Comment expectedComment = new Comment();
        expectedComment.setText(TEST_TEXT);
        expectedComment.setId(0);
        given(repository.findAll()).willReturn(Collections.singletonList(expectedComment));

        List<Comment> actualComments = service.findAll();

        assertThat(actualComments).containsExactly(expectedComment);
        then(repository).should(times(1)).findAll();
    }

    @Test
    void shouldFindNoCommentsIfEmpty() {
        given(repository.findAll()).willReturn(Collections.emptyList());

        List<Comment> actualComments = service.findAll();

        assertThat(actualComments).isEmpty();
        then(repository).should(times(1)).findAll();
    }
}