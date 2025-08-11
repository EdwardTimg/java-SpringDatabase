package com.example.databasePostgress.controllers;


import com.example.databasePostgress.TestDataUtil;
import com.example.databasePostgress.domain.dto.BookDto;
import com.example.databasePostgress.domain.entites.BookEntity;
import com.example.databasePostgress.services.BookServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookContollerIntegrationTests {


    private BookServices bookServices;
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
    @Autowired
    public BookContollerIntegrationTests(BookServices bookServices, MockMvc mockMvc) {
        this.bookServices = bookServices;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateBookReturnshttp201() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testThatCreateBookReturnsCreatedBook() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        )
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn()));
    }
@Test
    public void testThatFindAllBookReturnshttp200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFindAllBookReturnsListOfBooks() throws Exception {
        BookEntity testBook = TestDataUtil.createTestBook(null);
        bookServices.createUpdateBook(testBook.getIsbn(), testBook);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(testBook.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].isbn").value(testBook.getIsbn()));
    }

    @Test
    public void testThatGetByIdReturnshttp404IfBooksNotFound() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/books/99")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatUpdateBookReturnshttp200() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBook(null);
        bookServices.createUpdateBook(bookEntity.getIsbn(), bookEntity);

        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        bookDto.setIsbn(bookEntity.getIsbn());
        String bookJson = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatUpdateBookReturnsUpdatedBook() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBook(null);
        bookServices.createUpdateBook(bookEntity.getIsbn(), bookEntity);

        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        bookDto.setIsbn(bookEntity.getIsbn());
        bookDto.setTitle("Updated");
        String bookJson = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(bookJson)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn()));
    }
}
