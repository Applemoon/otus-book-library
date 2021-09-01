package ru.otus.library.dao;

import lombok.RequiredArgsConstructor;
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
        jdbc.update("INSERT INTO book (title, author_id, genre_id) values (:title, :author_id, :genre_id)",
                Map.of("title", book.getTitle(), "author_id", book.getAuthorId(),  "genre_id", book.getGenreId()));
    }

    public List<Book> findAll() {
        return jdbc.query("select * from book", new BookMapper());
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book book = new Book();
            book.setTitle(rs.getString("title"));
            book.setAuthorId(rs.getLong("author_id"));
            book.setGenreId(rs.getLong("genre_id"));
            return book;
        }
    }
}
