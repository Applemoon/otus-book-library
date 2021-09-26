package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.domain.Book;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository repository;

    public void create(String title) {
        if (title == null || title.isBlank()) return;
        Book book = new Book();
        book.setTitle(title);
        repository.create(book);
    }

    public List<Book> findAll() {
        return repository.findAll();
    }

    public Book findByTitle(String title) {
        if (title == null || title.isBlank()) return null;
        return repository.findByTitle(title);
    }

    public Book findById(long id) {
        return repository.findById(id);
    }

    public void update(long id, String title) {
        if (title == null || title.isBlank()) return;
        repository.updateTitleById(id, title);
    }

    public boolean deleteById(long id) {
        return repository.deleteById(id);
    }
}
