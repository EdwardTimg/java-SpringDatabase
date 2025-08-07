package com.example.databasePostgress.doa;

import com.example.databasePostgress.DAO.impl.AuthorDaoImpl;
import com.example.databasePostgress.TestDataUtil;
import com.example.databasePostgress.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthorDoaImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImpl undertest;

    @Test
    public void testThatCreatedAuthorGeneratsTheCorrectSql(){
        Author author = TestDataUtil.createTestAuthor();

        undertest.create(author);

        verify(jdbcTemplate).update(
                eq("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)"),
                eq(1l), eq("Tove Jansson"), eq(102)
        );
    }



    @Test
    public void testThatFindOneGeneratesTheCorrectSql(){
        undertest.findOne(1l);

        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors WHERE id=? LIMIT 1"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any()
                , eq(1L));
    }

    @Test
    public void testThatFindManyGeneratesTheCorrectSql(){
        undertest.find();
        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors")
                , ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any()
        );
    }

    @Test
    public void testThatUpdateGeneratsTheCorrectSql(){
        Author author = TestDataUtil.createTestAuthor();
        undertest.update(author.getId(), author);

        verify(jdbcTemplate).update(
                eq("UPDATE authors SET id = ?, name = ?, age = ? Where id = ?"),
                eq(author.getId()),eq(author.getName()),eq(author.getAge()),eq(author.getId())
        );
    }

    @Test
    public void testThatDeleteGernerastTheCorrectSql(){
        undertest.delete(1L);

        verify(jdbcTemplate).update(
                eq("DELETE FROM authors WHERE id = ?"),
                eq(1L)
        );
    }

}
