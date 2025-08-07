package com.example.databasePostgress.doa;

import com.example.databasePostgress.DAO.impl.BookDaoImpl;
import com.example.databasePostgress.TestDataUtil;
import com.example.databasePostgress.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class bookDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImpl underTest;

    @Test
    public void testThatCreatBookGenerateTheCorrectSql(){
        Book book = TestDataUtil.createTestBook();
        underTest.create(book);

        verify(jdbcTemplate).update(
                eq("INSERT INTO books (isbn, title, author_id) VALUES (?,?,?)"),
                eq("102"),eq("pappan och havet"),eq(1L));
    }

    @Test
    public void testThatFindOneGeneratsCorrectSql(){
        underTest.findOne( "102");
        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books where isbn=? LIMIT 1"),
                ArgumentMatchers.<BookDaoImpl.BooksRowMapper>any(),
                eq("102")
        );
    }

    @Test
    public void testThatFindGeneratesCorrectSql(){
        underTest.find();
        verify(jdbcTemplate).query(eq("SELECT isbn, title, author_id FROM books"),
                ArgumentMatchers.<BookDaoImpl.BooksRowMapper>any());
    }

}

