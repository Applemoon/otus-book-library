package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.domain.Comment;
import ru.otus.library.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository repository;

    public void create(String text) {
        if (text == null || text.isBlank()) return;
        repository.save(new Comment(text));
    }

    public Comment findByText(String text) {
        return repository.findByText(text);
    }

    public List<Comment> findAll() {
        return repository.findAll();
    }
}
