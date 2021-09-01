package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.dao.BookDao;
import ru.otus.library.domain.Book;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookDao dao;

    public void create(String title, long authorId, long genreId) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthorId(authorId);
        book.setGenreId(genreId);
        dao.create(book);
    }

    public List<Book> findAll() {
        return dao.findAll();
    }
}
