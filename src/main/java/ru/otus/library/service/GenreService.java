package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.dao.GenreDao;
import ru.otus.library.domain.Genre;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreDao dao;

    public void create(String name) {
        if (name == null || name.isBlank()) return;
        Genre genre = new Genre();
        genre.setName(name);
        dao.create(genre);
    }

    public Genre findByName(String name) {
        return dao.findByName(name);
    }
}
