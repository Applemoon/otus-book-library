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
        Author author = new Author();
        author.setName(name);
        repository.save(author);
    }

    public Author findByName(String name) {
        return repository.findByName(name);
    }

    public List<Author> findAll() {
        return repository.findAll();
    }
}
