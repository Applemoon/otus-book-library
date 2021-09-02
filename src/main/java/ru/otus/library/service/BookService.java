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
        if (title == null || title.isBlank()) return;
        Book book = new Book();
        book.setTitle(title);
        book.setAuthorId(authorId);
        book.setGenreId(genreId);
        dao.create(book);
    }

    public List<Book> findAll() {
        return dao.findAll();
    }

    public Book findByTitle(String title) {
        if (title == null || title.isBlank()) return null;
        return dao.findByTitle(title);
    }

    public Book findById(long id) {
        return dao.findById(id);
    }

    public void update(long id, String title, long authorId, long genreId) {
        if (title == null || title.isBlank()) return;
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthorId(authorId);
        book.setGenreId(genreId);
        dao.update(book);
    }

    public boolean deleteById(long id) {
        return dao.deleteById(id);
    }
}
