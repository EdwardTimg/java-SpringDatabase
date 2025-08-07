package com.example.databasePostgress.doa;

import com.example.databasePostgress.DAO.AuthorDao;
import com.example.databasePostgress.DAO.impl.BookDaoImpl;
import com.example.databasePostgress.TestDataUtil;
import com.example.databasePostgress.domain.Author;
import com.example.databasePostgress.domain.Book;
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
public class BookDaoImplIntegrationTests {

    private BookDaoImpl underTest;
    private AuthorDao   authorDao;

    @Autowired
    public BookDaoImplIntegrationTests(BookDaoImpl underTest, AuthorDao authorDao){
        this.underTest = underTest;
        this.authorDao = authorDao;
    }

    @Test
    public void testThatBookCanBeCreatedAndRead(){
        Book book = TestDataUtil.createTestBook();
        Author author = TestDataUtil.createTestAuthor();
        authorDao.create(author);
        book.setAuthorId(author.getId());
        underTest.create(book);
        Optional<Book> result = underTest.findOne(book.getIsbn());
        assert(result.isPresent());
        assert(result.get().equals(book));
    }

    @Test
    public void testThatFindManyBooksWork(){
        Author author = TestDataUtil.createTestAuthor();
        authorDao.create(author);
        Book bookA = TestDataUtil.createTestBook();
        bookA.setAuthorId(author.getId());
        underTest.create(bookA);
        Book bookB = TestDataUtil.createTestBookb();
        bookB.setAuthorId(author.getId());
        underTest.create(bookB);

        List<Book> result = underTest.find();
        assertThat(result).hasSize(2).containsExactly(bookA,bookB);
    }
}
