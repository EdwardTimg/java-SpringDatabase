package com.example.databasePostgress.services;

import com.example.databasePostgress.domain.dto.AuthorDto;
import com.example.databasePostgress.domain.entites.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorServices {

    AuthorEntity saveAuthor(AuthorEntity authorEntity);

    List<AuthorEntity> findAll();

    Optional<AuthorEntity> getById(Long id);

    boolean isExists(Long id);

    AuthorEntity partialUpdate(Long id, AuthorDto authorDto);

    void delete(Long id);
}
