package com.example.databasePostgress.DAO.impl;

import com.example.databasePostgress.DAO.AuthorDao;
import com.example.databasePostgress.domain.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final JdbcTemplate jdbcTemplate;
    public AuthorDaoImpl(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Author author) {
        jdbcTemplate.update("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)",
                author.getId(),
                author.getName(),
                author.getAge()
                );
    }

    @Override
    public Optional<Author> findOne(Long authorId) {
        List<Author> results=  jdbcTemplate.query("SELECT id, name, age FROM authors WHERE id=? LIMIT 1",
                new AuthorRowMapper(), authorId);

        return results.stream().findFirst();
    }

    @Override
    public List<Author> find() {
        List<Author> results = jdbcTemplate.query("SELECT id, name, age FROM authors", new AuthorRowMapper());
        return results;
    }

    @Override
    public void update(Long id, Author author) {
        jdbcTemplate.update("UPDATE authors SET id = ?, name = ?, age = ? Where id = ?",
        author.getId(),
        author.getName(),
        author.getAge(),
        id
        );
    }

    public static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Author.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .age(rs.getInt("age"))
                    .build();
        }
    }
}
