package com.example.databasePostgress.repositories;


import com.example.databasePostgress.TestDataUtil;
import com.example.databasePostgress.domain.entites.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorEntityRepositoryIntegrationTests {

    private AuthorRepository undertest;

    @Autowired
    public AuthorEntityRepositoryIntegrationTests(AuthorRepository underTest){
        this.undertest = underTest;
    }
    @Test
    public void testThatAuthorCanBeCreated(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        undertest.save(authorEntity);
        Optional<AuthorEntity> result =undertest.findById(authorEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntity);
    }

    @Test
    public void testThatManyAuthorsCanBeReturned(){
     AuthorEntity authorEntityA = TestDataUtil.createTestAuthor();
     undertest.save(authorEntityA);
     AuthorEntity authorEntityB = TestDataUtil.createTestAuthorB();
     undertest.save(authorEntityB);
     AuthorEntity authorEntityC = TestDataUtil.createTestAuthorC();
     undertest.save(authorEntityC);

     Iterable<AuthorEntity> result = undertest.findAll();
     assertThat(result).hasSize(3).containsExactly(authorEntityA, authorEntityB, authorEntityC);
    }


    @Test
    public void testThatAuthorCanBeUpdated(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        undertest.save(authorEntity);
        authorEntity.setName("UPDATED");
        undertest.save(authorEntity);

        Iterable<AuthorEntity> result = undertest.findAll();
        assertThat(result).hasSize(1).contains(authorEntity);
    }

    @Test
    public void testThatAuthorCanBeDeleted(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        undertest.save(authorEntity);

        undertest.deleteById(authorEntity.getId());

        Optional<AuthorEntity> result = undertest.findById(authorEntity.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void testThatGetAuthorsWithAgeLessThanK(){
        AuthorEntity testAuthorEntity = TestDataUtil.createTestAuthor();
        undertest.save(testAuthorEntity);
        AuthorEntity testAuthorEntityB = TestDataUtil.createTestAuthorB();
        undertest.save(testAuthorEntityB);
        AuthorEntity testAuthorEntityC = TestDataUtil.createTestAuthorC();
        undertest.save(testAuthorEntityC);

        Iterable<AuthorEntity> result = undertest.ageLessThan(105);
        assertThat(result).containsExactly(testAuthorEntity, testAuthorEntityC);
    }

    @Test
    public void testThatGetAuthorsWithAgeGreaterThan(){
        AuthorEntity testAuthorEntity = TestDataUtil.createTestAuthor();
        undertest.save(testAuthorEntity);
        AuthorEntity testAuthorEntityB = TestDataUtil.createTestAuthorB();
        undertest.save(testAuthorEntityB);
        AuthorEntity testAuthorEntityC = TestDataUtil.createTestAuthorC();
        undertest.save(testAuthorEntityC);

        Iterable<AuthorEntity> result = undertest.findAuthorGreaterThan(105);
        assertThat(result).containsExactly(testAuthorEntityB);
    }
}
