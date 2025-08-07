package com.example.databasePostgress.DAO;

import com.example.databasePostgress.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    void create(Author author);

    Optional<Author> findOne(Long l);

    List<Author> find();

    void update(Long id, Author author);

    void delete(long l);
}
