package com.example.databasePostgress;

import com.example.databasePostgress.domain.dto.AuthorDto;
import com.example.databasePostgress.domain.dto.BookDto;
import com.example.databasePostgress.domain.entites.AuthorEntity;
import com.example.databasePostgress.domain.entites.BookEntity;

public final class TestDataUtil {

    private TestDataUtil(){}
    public static AuthorEntity createTestAuthor() {
        return AuthorEntity.builder()
                .name("Tove Jansson")
                .age(102)
                .build();
    }
    public static AuthorEntity createTestAuthorB() {
        return AuthorEntity.builder()
                .name("Strindberg")
                .age(150)
                .build();
    }
    public static AuthorEntity createTestAuthorC() {
        return AuthorEntity.builder()
                .name("Scarrow")
                .age(50)
                .build();
    }


    public static BookEntity createTestBook(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("102")
                .title("pappan och havet")
                .authorEntity(authorEntity)
                .build();
    }
    public static BookEntity createTestBookb(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("103")
                .title("Vem ska trösta knyttet")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookDto createTestBookDto(final AuthorDto author) {
        return BookDto.builder()
                .isbn("102")
                .title("pappan och havet")
                .author(author)
                .build();
    }
}
