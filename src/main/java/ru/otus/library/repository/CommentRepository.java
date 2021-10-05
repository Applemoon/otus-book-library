package ru.otus.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByText(String text);
}
