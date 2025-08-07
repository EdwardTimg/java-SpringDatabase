package com.example.databasePostgress;

import com.example.databasePostgress.domain.Author;
import com.example.databasePostgress.domain.Book;

public final class TestDataUtil {

    private TestDataUtil(){}
    public static Author createTestAuthor() {
        return Author.builder()
                .id(1L)
                .name("Tove Jansson")
                .age(102)
                .build();
    }
    public static Author createTestAuthorB() {
        return Author.builder()
                .id(2L)
                .name("Strindberg")
                .age(150)
                .build();
    }
    public static Author createTestAuthorC() {
        return Author.builder()
                .id(3L)
                .name("Scarrow")
                .age(50)
                .build();
    }


    public static Book createTestBook() {
        return Book.builder()
                .isbn("102")
                .title("pappan och havet")
                .authorId(1L)
                .build();
    }
    public static Book createTestBookb() {
        return Book.builder()
                .isbn("103")
                .title("Vem ska trösta knyttet")
                .authorId(1L)
                .build();
    }
}
