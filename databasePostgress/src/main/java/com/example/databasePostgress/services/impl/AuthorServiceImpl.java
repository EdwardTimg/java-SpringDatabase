package com.example.databasePostgress.services.impl;

import com.example.databasePostgress.domain.dto.AuthorDto;
import com.example.databasePostgress.domain.entites.AuthorEntity;
import com.example.databasePostgress.repositories.AuthorRepository;
import com.example.databasePostgress.services.AuthorServices;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorServices {

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository){
        this.authorRepository =authorRepository;
    }

    @Override
    public AuthorEntity saveAuthor(AuthorEntity authorEntity) {
       return authorRepository.save(authorEntity);

    }

    @Override
    public List<AuthorEntity> findAll() {
        return StreamSupport.stream(authorRepository
                .findAll().spliterator(),false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorEntity> getById(Long id) {
        return authorRepository.findById(id);
    }


    @Override
    public boolean isExists(Long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public AuthorEntity partialUpdate(Long id, AuthorDto authorDto) {
        authorDto.setId(id);
        return authorRepository.findById(id).map(existingAuthor ->{
            Optional.ofNullable(authorDto.getAge()).ifPresent(existingAuthor::setAge);
            Optional.ofNullable(authorDto.getName()).ifPresent(existingAuthor::setName);
            return authorRepository.save(existingAuthor);
        }).orElseThrow(() -> new RuntimeException("Auhtor Do not Exist") );
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
}
