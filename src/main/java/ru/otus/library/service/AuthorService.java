package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.dao.AuthorDao;
import ru.otus.library.domain.Author;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorDao dao;

    public void create(String name) {
        if (name == null || name.isBlank()) return;
        Author author = new Author();
        author.setName(name);
        dao.create(author);
    }

    public Author findByName(String name) {
        return dao.findByName(name);
    }

    public List<Author> findAll() {
        return dao.findAll();
    }
}
