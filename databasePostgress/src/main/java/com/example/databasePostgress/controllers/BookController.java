package com.example.databasePostgress.controllers;

import com.example.databasePostgress.domain.dto.BookDto;
import com.example.databasePostgress.domain.entites.BookEntity;
import com.example.databasePostgress.mappers.Mapper;
import com.example.databasePostgress.services.BookServices;
import com.example.databasePostgress.services.impl.BookServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookController {


    private BookServices bookServices;
    private Mapper<BookEntity, BookDto> bookMapper;

    public BookController(BookServices bookServices, Mapper<BookEntity, BookDto> bookMapper) {
        this.bookServices = bookServices;
        this.bookMapper = bookMapper;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto bookDto){
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBookEntity = bookServices.createBook(isbn,bookEntity);
        return new ResponseEntity<>(bookMapper.mapTo(savedBookEntity), HttpStatus.CREATED);
    }

    @GetMapping("/books")
    public List<BookDto> listbooks(){
        List<BookEntity> books = bookServices.findAll();
        return books.stream().map(bookMapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping("/books/{isbn}")
    public ResponseEntity<BookDto> getBooks(@PathVariable("isbn") String isbn){
        Optional<BookEntity> bookFound = bookServices.getById(isbn);
        return bookFound.map(bookEntity -> {
            BookDto bookDto = bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
}
