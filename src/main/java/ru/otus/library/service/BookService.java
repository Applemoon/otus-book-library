package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.domain.Book;
import ru.otus.library.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository repository;

    public void create(String title, long authorId, long genreId) {
        if (title == null || title.isBlank()) return;
        Book book = new Book();
        book.setTitle(title);
        book.setAuthorId(authorId);
        book.setGenreId(genreId);
        repository.save(book);
    }

    public List<Book> findAll() {
        return repository.findAll();
    }

    public Book findByTitle(String title) {
        if (title == null || title.isBlank()) return null;
        return repository.findByTitle(title);
    }

    public Book findById(long id) {
        return repository.findById(id).orElse(null);
    }

    public void update(long id, String title, long authorId, long genreId) {
        if (title == null || title.isBlank()) return;
        Book book = repository.findById(id).orElse(new Book());
        book.setTitle(title);
        book.setAuthorId(authorId);
        book.setGenreId(genreId);
        repository.save(book);
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
