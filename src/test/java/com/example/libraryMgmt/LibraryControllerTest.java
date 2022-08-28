package com.example.libraryMgmt;

import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;
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
import com.example.librarymgmt.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
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

    // @Test
    // void shouldReturnEmpty() throws Exception{
    //     String uri = "/books";
    //     MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
    //     .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    //     Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
    //     String result  = mvcResult.getResponse().getContentAsString();

    //     Assertions.assertEquals(result,"");
    // }

    @Test
    void shouldReturnBooks() throws Exception{
        String uri = "/books";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String result  = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        List<Book> booklist = objectMapper.readValue(result, new TypeReference<List<Book>>(){});
        Assertions.assertTrue(booklist.size() > 0);
    }

    @Test
    void shouldAddBookToBorrowList() throws Exception{
        User user = new User(345, "Alex", "Crossing Street", null);
        ObjectMapper objectMapper=new ObjectMapper();
        String userString = objectMapper.writeValueAsString(user);
        String uri = "/book/add/{bookid}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
            .put(uri, 44234)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(userString)
        )
        .andReturn();

        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String result  = mvcResult.getResponse().getContentAsString();

        ObjectMapper jsonMapper = new ObjectMapper();
        User resultUser = jsonMapper.readValue(result, User.class);
        Assertions.assertTrue(resultUser.getBorrowed().size()>0);
    }


    @Test
    void shouldRemoveBookFromLibrary() throws Exception{
        User user = new User(345, "Alex", "Crossing Street", null);
        int bookId = 44234;
        ObjectMapper objectMapper=new ObjectMapper();
        String userString = objectMapper.writeValueAsString(user);
        String uri = "/book/add/{bookid}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
            .put(uri, bookId)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(userString)
        )
        .andReturn();

        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String result  = mvcResult.getResponse().getContentAsString();

        ObjectMapper jsonMapper = new ObjectMapper();
        User resultUser = jsonMapper.readValue(result, User.class);

        Assertions.assertTrue(resultUser.getBorrowed().size()>0);

        MvcResult mvcResult1 = mvc.perform(MockMvcRequestBuilders.get("/books")
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        Assertions.assertEquals(200, mvcResult1.getResponse().getStatus());
        String books  = mvcResult1.getResponse().getContentAsString();

        List<Book> bookList = objectMapper.readValue(books,new TypeReference<List<Book>>(){});

        var bookMatched = bookList.stream().filter(book->book.getId()==bookId).collect(Collectors.toList());
        
        Assertions.assertEquals(bookMatched.size(),0);

    }
}
