package ru.otus.library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class GenreDao {
    private final NamedParameterJdbcOperations jdbc;

    public void create(Genre genre) {
        if (genre == null) return;
        jdbc.update("INSERT INTO genre (name) values (:name)", Map.of("name", genre.getName()));
    }

    public Genre findByName(String name) {
        try {
            return jdbc.queryForObject("SELECT * FROM genre WHERE name = :name", Map.of("name", name), new GenreMapper());
        } catch (EmptyResultDataAccessException e) {
            System.err.println("No genre with name " + name);
            return null;
        }
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            Genre genre = new Genre();
            genre.setName(rs.getString("name"));
            return genre;
        }
    }
}
