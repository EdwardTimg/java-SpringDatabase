package com.example.databasePostgress.controllers;

import com.example.databasePostgress.domain.dto.AuthorDto;
import com.example.databasePostgress.domain.entites.AuthorEntity;
import com.example.databasePostgress.mappers.Mapper;
import com.example.databasePostgress.services.AuthorServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AuthorController {

    private AuthorServices authorServices;

    private Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(AuthorServices authorServices, Mapper<AuthorEntity, AuthorDto> authorMapper){
        this.authorServices = authorServices;
        this.authorMapper =authorMapper;
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author){
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity saveAuthorEntity = authorServices.saveAuthor(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(saveAuthorEntity), HttpStatus.CREATED);
    }

    @GetMapping(path =  "/authors")
    public List<AuthorDto> listAuthors(){
        List<AuthorEntity> authers = authorServices.findAll();
        return authers.stream().map(authorMapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping(path ="/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") Long id){
        Optional<AuthorEntity> foudnAuthor = authorServices.getById(id);
         return foudnAuthor.map(authorEntity -> {
             AuthorDto authorDto = authorMapper.mapTo(authorEntity);
             return new ResponseEntity<>(authorDto, HttpStatus.OK);
         }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(
            @PathVariable("id") Long id,
            @RequestBody AuthorDto author){
        if(!authorServices.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        author.setId(id);
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity savedAuthor = authorServices.saveAuthor(authorEntity);
        return  new ResponseEntity<>(authorMapper.mapTo(savedAuthor),HttpStatus.OK);

    }

    @PatchMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> partialUpdate(
            @PathVariable("id") Long id,
            @RequestBody AuthorDto authorDto){
        if(!authorServices.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity updatedAuthor = authorServices.partialUpdate(id, authorDto);

        return  new ResponseEntity<>(authorMapper.mapTo(updatedAuthor), HttpStatus.OK);
    }

    @DeleteMapping(path = "/authors/{id}")
    public ResponseEntity deleteAuthro(@PathVariable("id") Long id){
        authorServices.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
