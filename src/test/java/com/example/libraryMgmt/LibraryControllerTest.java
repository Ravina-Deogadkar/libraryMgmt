package com.example.libraryMgmt;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.librarymgmt.controller.LibraryController;
import com.example.librarymgmt.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@WebAppConfiguration
public class LibraryControllerTest {
    protected MockMvc mvc;
    @Autowired
    LibraryController libraryController;

    @Autowired
   WebApplicationContext webApplicationContext;

   @BeforeEach
    protected void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
   
    @Test
    void shouldReturnHello(){
        Assertions.assertEquals("Hello", libraryController.helloString());
    }

    @Test
    void shouldReturnEmpty() throws Exception{
        String uri = "/books";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String result  = mvcResult.getResponse().getContentAsString();

        Assertions.assertEquals(result,"");
    }

    @Test
    void shouldReturnBooks() throws Exception{
        String uri = "/books";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String result  = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        List<Book> booklist = objectMapper.readValue(result, List.class);
        Assertions.assertTrue(booklist.size() > 0);
    }
}
