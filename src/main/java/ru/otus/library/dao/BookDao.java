package ru.otus.library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BookDao {
    private final NamedParameterJdbcOperations jdbc;

    public void create(Book book) {
        if (book == null) return;
        jdbc.update("insert into book (title, author_id, genre_id) values (:title, :author_id, :genre_id)",
                Map.of("title", book.getTitle(), "author_id", book.getAuthorId(), "genre_id", book.getGenreId()));
    }

    public List<Book> findAll() {
        return jdbc.query("select * from book", new BookMapper());
    }

    public Book findByTitle(String title) {
        try {
            return jdbc.queryForObject("select * from book where title = :title", Map.of("title", title), new BookMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Book findById(long id) {
        try {
            return jdbc.queryForObject("select * from book where id = :id", Map.of("id", id), new BookMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void update(Book book) {
        if (book == null) return;
        jdbc.update("update book set title = :title, author_id = :author_id, genre_id = :genre_id where id = :id",
                Map.of("title", book.getTitle(),
                        "author_id", book.getAuthorId(),
                        "genre_id", book.getGenreId(),
                        "id", book.getId()));
    }

    public boolean deleteById(long id) {
        return jdbc.update("delete from book where id = :id", Map.of("id", id)) != 0;
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book book = new Book();
            book.setId(rs.getLong("id"));
            book.setTitle(rs.getString("title"));
            book.setAuthorId(rs.getLong("author_id"));
            book.setGenreId(rs.getLong("genre_id"));
            return book;
        }
    }
}
