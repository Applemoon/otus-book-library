package ru.otus.library.dao;

import lombok.RequiredArgsConstructor;
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
        jdbc.update("INSERT INTO author (name) values (:name)", Map.of("name", author.getName()));
    }

    public Author findByName(String name) {
        return jdbc.queryForObject("SELECT * FROM author WHERE name = :name", Map.of("name", name), new AuthorMapper());
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
