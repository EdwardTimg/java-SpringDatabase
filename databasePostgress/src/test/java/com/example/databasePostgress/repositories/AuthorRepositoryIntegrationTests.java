package com.example.databasePostgress.repositories;


import com.example.databasePostgress.TestDataUtil;
import com.example.databasePostgress.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTests {

    private AuthorRepository undertest;

    @Autowired
    public AuthorRepositoryIntegrationTests(AuthorRepository underTest){
        this.undertest = underTest;
    }
    @Test
    public void testThatAuthorCanBeCreated(){
        Author author = TestDataUtil.createTestAuthor();
        undertest.save(author);
        Optional<Author> result =undertest.findById(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatManyAuthorsCanBeReturned(){
     Author authorA = TestDataUtil.createTestAuthor();
     undertest.save(authorA);
     Author authorB = TestDataUtil.createTestAuthorB();
     undertest.save(authorB);
     Author authorC = TestDataUtil.createTestAuthorC();
     undertest.save(authorC);

     Iterable<Author> result = undertest.findAll();
     assertThat(result).hasSize(3).containsExactly(authorA,authorB, authorC);
    }


    @Test
    public void testThatAuthorCanBeUpdated(){
        Author author = TestDataUtil.createTestAuthor();
        undertest.save(author);
        author.setName("UPDATED");
        undertest.save(author);

        Iterable<Author> result = undertest.findAll();
        assertThat(result).hasSize(1).contains(author);
    }

    @Test
    public void testThatAuthorCanBeDeleted(){
        Author author = TestDataUtil.createTestAuthor();
        undertest.save(author);

        undertest.deleteById(author.getId());

        Optional<Author> result = undertest.findById(author.getId());
        assertThat(result).isEmpty();
    }
}
