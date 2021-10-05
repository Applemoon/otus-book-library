package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.repository.GenreRepository;
import ru.otus.library.domain.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository repository;

    public void create(String name) {
        if (name == null || name.isBlank()) return;
        Genre genre = new Genre();
        genre.setName(name);
        repository.save(genre);
    }

    public Genre findByName(String name) {
        return repository.findByName(name);
    }

    public List<Genre> findAll() {
        return repository.findAll();
    }
}
