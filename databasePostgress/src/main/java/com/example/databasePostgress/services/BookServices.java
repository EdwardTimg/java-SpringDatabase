package com.example.databasePostgress.services;

import com.example.databasePostgress.domain.entites.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookServices {

    BookEntity createBook(String isbn, BookEntity bookEntity);

    List<BookEntity> findAll();

    Optional<BookEntity> getById(String isbn);
}
