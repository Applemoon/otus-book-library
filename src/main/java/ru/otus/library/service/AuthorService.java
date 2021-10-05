package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.domain.Author;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository repository;

    public void create(String name) {
        if (name == null || name.isBlank()) return;
        repository.save(new Author(name));
    }

    public Author findByName(String name) {
        return repository.findByName(name);
    }

    public List<Author> findAll() {
        return repository.findAll();
    }
}
