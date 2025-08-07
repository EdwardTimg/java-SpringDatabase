package com.example.databasePostgress.doa;

import com.example.databasePostgress.DAO.impl.AuthorDaoImpl;
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
public class AuthorDaoImplIntegrationTests {

    private AuthorDaoImpl undertest;

    @Autowired
    public AuthorDaoImplIntegrationTests(AuthorDaoImpl underTest){
        this.undertest = underTest;
    }
    @Test
    public void testThatAuthorCanBeCreated(){
        Author author = TestDataUtil.createTestAuthor();
        undertest.create(author);
        Optional<Author> result =undertest.findOne(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatManyAuthorsCanBeReturned(){
     Author authorA = TestDataUtil.createTestAuthor();
     undertest.create(authorA);
     Author authorB = TestDataUtil.createTestAuthorB();
     undertest.create(authorB);
     Author authorC = TestDataUtil.createTestAuthorC();
     undertest.create(authorC);

     List<Author> result = undertest.find();
     assertThat(result).hasSize(3).containsExactly(authorA,authorB, authorC);
    }


    @Test
    public void testThatAuthorCanBeUpdated(){
        Author author = TestDataUtil.createTestAuthor();
        undertest.create(author);
        Author authorB = TestDataUtil.createTestAuthorB();

        undertest.update(author.getId(),authorB);

        List<Author> result = undertest.find();
        assertThat(result).hasSize(1).contains(authorB);
    }

    @Test
    public void testThatAuthorCanBeDeleted(){
        Author author = TestDataUtil.createTestAuthor();
        undertest.create(author);

        undertest.delete(author.getId());

        Optional<Author> result = undertest.findOne(author.getId());
        assertThat(result).isEmpty();
    }
}
