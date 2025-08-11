package com.example.databasePostgress.controllers;

import com.example.databasePostgress.TestDataUtil;
import com.example.databasePostgress.domain.entites.AuthorEntity;
import com.example.databasePostgress.services.AuthorServices;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllersIntegrationTests {


    private AuthorServices authorServices;
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public AuthorControllersIntegrationTests(AuthorServices authorServices, MockMvc mockMvc) {
        this.authorServices = authorServices;
        this.mockMvc = mockMvc;
        this.objectMapper =  new ObjectMapper();
    }

    @Test
    public void testThtatCreateAuthorSuccessfullyReturn201() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
        String authorJson = objectMapper.writeValueAsString(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }


    @Test
    public void testThtatCreateAuthorSuccessfullyReturnSavedAuthor() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
        String authorJson = objectMapper.writeValueAsString(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        )
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Tove Jansson"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(102));
    }

    @Test
    public void testThatFindAllReturnsHttp200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFindAllReturnsListOfAuther() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
        authorServices.saveAuthor(testAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Tove Jansson"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(102));
    }


    @Test
    public void testThaGetByIdReturnsHttp200WhenFound() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
        authorServices.saveAuthor(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/"+testAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                        .value(testAuthor.getName()));
    }

    @Test
    public void testThaGetByIdReturnsHttp404WhenAuthorNOTFound() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    public void testThatItsPossibleToUpdateAuther() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
        authorServices.saveAuthor(testAuthor);
        testAuthor.setName("Updated");
        String authorJson = objectMapper.writeValueAsString(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/"+testAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated"));
    }

    @Test
    public void testThatPutAutherResponseWith404IfAuthorNotFound() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
        testAuthor.setName("Updated");
        String authorJson = objectMapper.writeValueAsString(testAuthor);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/authors/999")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(authorJson)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    public void testThatPartialUpdateReturnsHttp200AndCorrectBody() throws Exception {

        AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
        authorServices.saveAuthor(testAuthor);
        testAuthor.setName("Updated");
        String authorJson = objectMapper.writeValueAsString(testAuthor);
        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/authors/"+testAuthor.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(authorJson)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated"));
    }

    @Test
    public void testThatDeleteAuthorReturnStatus204ForNoneExistingAuthor() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/22")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteAcuallyDeletes() throws Exception {
        AuthorEntity testauthor = TestDataUtil.createTestAuthor();
        authorServices.saveAuthor(testauthor);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/"+ testauthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());

        assertThat(authorServices.isExists(testauthor.getId())).isFalse();

    }
}

