package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.dao.AuthorDao;
import ru.otus.library.domain.Author;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorDao authorDao;

    public void create(String name) {
        Author author = new Author();
        author.setName(name);
        authorDao.create(author);
    }

    public Author findByName(String name) {
        return authorDao.findByName(name);
    }
}
