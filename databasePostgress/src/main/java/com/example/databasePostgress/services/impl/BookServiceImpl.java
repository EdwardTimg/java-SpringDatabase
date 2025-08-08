package com.example.databasePostgress.services.impl;

import com.example.databasePostgress.domain.entites.BookEntity;
import com.example.databasePostgress.repositories.BookRepository;
import com.example.databasePostgress.services.BookServices;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookServices {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createBook(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);
        return bookRepository.save(bookEntity);
    }

    @Override
    public List<BookEntity> findAll() {
        return StreamSupport.stream(bookRepository
                        .findAll().spliterator(),false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BookEntity> getById(String isbn) {
        return bookRepository.findById(isbn);
    }
}
