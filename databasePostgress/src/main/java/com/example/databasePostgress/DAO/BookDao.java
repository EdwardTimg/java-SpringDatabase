package com.example.databasePostgress.DAO;

import com.example.databasePostgress.domain.Book;

import java.util.Optional;

public interface BookDao {
    void create(Book book);

    Optional<Book> findOne(String bookIsbn);
}
