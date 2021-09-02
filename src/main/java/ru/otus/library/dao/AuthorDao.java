package ru.otus.library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AuthorDao {
    private final NamedParameterJdbcOperations jdbc;

    public void create(Author author) {
        if (author == null) return;
        jdbc.update("INSERT INTO author (name) values (:name)", Map.of("name", author.getName()));
    }

    public Author findByName(String name) {
        try {
            return jdbc.queryForObject("SELECT * FROM author WHERE name = :name", Map.of("name", name), new AuthorMapper());
        } catch (EmptyResultDataAccessException e) {
            System.err.println("No author with name " + name);
            return null;
        }
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            Author author = new Author();
            author.setName(rs.getString("name"));
            return author;
        }
    }
}
