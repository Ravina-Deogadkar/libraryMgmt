package com.example.libraryMgmt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.example.librarymgmt.exception.BookUnavailableException;
import com.example.librarymgmt.exception.BorrowLimitException;
import com.example.librarymgmt.exception.DuplicateCopyBorrowException;
import com.example.librarymgmt.model.Book;
import com.example.librarymgmt.model.User;
import com.example.librarymgmt.service.LibraryService;
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

   @Autowired
   LibraryService libraryService;

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
        String uri = "/books/add/{bookid}";
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
    void shouldAddOnlyTwoBooksToBorrowList() throws Exception{
        var borrowList = new ArrayList<Integer>();
        borrowList.add(44234);
        borrowList.add(43562);
        User user = new User(345, "Alex", "Crossing Street", borrowList);
        ObjectMapper objectMapper=new ObjectMapper();
        String userString = objectMapper.writeValueAsString(user);
        String uri = "/books/add/{bookid}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
            .put(uri, 44234)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(userString)
        ).andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof BorrowLimitException))
        
        .andReturn();

        Assertions.assertEquals(400, mvcResult.getResponse().getStatus());

        Assertions.assertEquals(mvcResult.getResponse().getErrorMessage(), "Borrow list already have two books");

    }

    @Test
    void shouldAddOnlyOneCopyOfBookToBorrowList() throws Exception{
        var borrowList = new ArrayList<Integer>();
        borrowList.add(44234);
        User user = new User(345, "Alex", "Crossing Street", borrowList);
        ObjectMapper objectMapper=new ObjectMapper();
        String userString = objectMapper.writeValueAsString(user);
        String uri = "/books/add/{bookid}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
            .put(uri, 44234)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(userString)
        ).andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof DuplicateCopyBorrowException))
        .andReturn();

        
        Assertions.assertEquals(400, mvcResult.getResponse().getStatus());

        Assertions.assertEquals(mvcResult.getResponse().getErrorMessage(), "Copy already exist in borrow list");
    }


    @Test
    void shouldNotAddBookToBorrowList() throws Exception{
        var borrowList = new ArrayList<Integer>();
        borrowList.add(42321);
        User user = new User(345, "Alex", "Crossing Street", borrowList);
        ObjectMapper objectMapper=new ObjectMapper();
        String userString = objectMapper.writeValueAsString(user);
        String uri = "/books/add/{bookid}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
            .put(uri, 88231)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(userString)
        ).andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof BookUnavailableException))
        .andReturn();

        
        Assertions.assertEquals(400, mvcResult.getResponse().getStatus());

        Assertions.assertEquals(mvcResult.getResponse().getErrorMessage(), "Book unavailable");
    }

    @Test
    void shouldRemoveBookFromLibrary() throws Exception{
        
        Book newbook = new Book(45556,"Alice in Wonderland", "Alice in Wonderland", "2006-10-21","",'Y',1);
        
        libraryService.setBook(newbook);
        Thread.sleep(3000);

        User user = new User(345, "Alex", "Crossing Street", null);
        ObjectMapper objectMapper=new ObjectMapper();
        String userString = objectMapper.writeValueAsString(user);
        String uri = "/books/add/{bookid}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
            .put(uri, newbook.getId())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(userString)
        )
        .andReturn();

        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String result  = mvcResult.getResponse().getContentAsString();

        ObjectMapper jsonMapper = new ObjectMapper();
        User resultUser = jsonMapper.readValue(result, User.class);

        Assertions.assertTrue(resultUser.getBorrowed().size()>0);
        Thread.sleep(3000);
        
        MvcResult mvcResult1 = mvc.perform(MockMvcRequestBuilders.get("/books")
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        Assertions.assertEquals(200, mvcResult1.getResponse().getStatus());
        String books  = mvcResult1.getResponse().getContentAsString();

        List<Book> bookList = objectMapper.readValue(books,new TypeReference<List<Book>>(){});

        var bookMatched = bookList.stream().filter(book->book.getId()==newbook.getId()).collect(Collectors.toList());
        
        Assertions.assertEquals(0,bookMatched.size());

    }

    @Test
    void shouldReduceCopyAvailableCount() throws Exception{
       
        User user = new User(345, "Alex", "Crossing Street", null);
        int bookId = 43562;

        ObjectMapper objectMapper=new ObjectMapper();

        MvcResult mvcResult1 = mvc.perform(MockMvcRequestBuilders.get("/books")
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        Assertions.assertEquals(200, mvcResult1.getResponse().getStatus());
        String books  = mvcResult1.getResponse().getContentAsString();

        List<Book> bookList = objectMapper.readValue(books,new TypeReference<List<Book>>(){});

        var bookBefore = bookList.stream().filter(book->book.getId()==bookId).collect(Collectors.toList());
        
        String userString = objectMapper.writeValueAsString(user);
        String uri = "/books/add/{bookid}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
            .put(uri, bookId)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(userString)
        )
        .andReturn();

        Assertions.assertEquals(200,mvcResult.getResponse().getStatus());
        Thread.sleep(2000);
        MvcResult mvcResult2 = mvc.perform(MockMvcRequestBuilders.get("/books")
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        Assertions.assertEquals(200, mvcResult1.getResponse().getStatus());
        String booksJson = mvcResult2.getResponse().getContentAsString();

        List<Book> bookListAfter = objectMapper.readValue(booksJson,new TypeReference<List<Book>>(){});

        var bookAfter = bookListAfter.stream().filter(book->book.getId()==bookId).collect(Collectors.toList());
        
        Assertions.assertTrue(bookBefore.get(0).getCopyAvailable()>bookAfter.get(0).getCopyAvailable());
    }


    @Test
    void shouldIncreaseCopyAvailableCount() throws Exception{
       
        var borrowList = new ArrayList<Integer>();
        borrowList.add(44234);
        borrowList.add(43562);
        User user = new User(345, "Alex", "Crossing Street", borrowList);
        int bookId = 43562;

        ObjectMapper objectMapper=new ObjectMapper();

        MvcResult mvcResult1 = mvc.perform(MockMvcRequestBuilders.get("/books")
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        Assertions.assertEquals(200, mvcResult1.getResponse().getStatus());
        String books  = mvcResult1.getResponse().getContentAsString();

        List<Book> bookList = objectMapper.readValue(books,new TypeReference<List<Book>>(){});

        var bookBefore = bookList.stream().filter(book->book.getId()==bookId).collect(Collectors.toList());
        
        String userString = objectMapper.writeValueAsString(user);
        String uri = "/books/return/{bookid}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
            .put(uri, bookId)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(userString)
        )
        .andReturn();

        Assertions.assertEquals(200,mvcResult.getResponse().getStatus());

        Thread.sleep(2000);
        MvcResult mvcResult2 = mvc.perform(MockMvcRequestBuilders.get("/books")
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        Assertions.assertEquals(200, mvcResult1.getResponse().getStatus());
        String booksJson = mvcResult2.getResponse().getContentAsString();

        List<Book> bookListAfter = objectMapper.readValue(booksJson,new TypeReference<List<Book>>(){});

        var bookAfter = bookListAfter.stream().filter(book->book.getId()==bookId).collect(Collectors.toList());
        
        Assertions.assertTrue(bookBefore.get(0).getCopyAvailable()<bookAfter.get(0).getCopyAvailable());
    }


    @Test
    void shouldAddUnAvailableBookToLibrary() throws Exception{
       
        var borrowList = new ArrayList<Integer>();
        borrowList.add(44234);
        borrowList.add(45556);

        Book newbook = new Book(45556,"Alice in Wonderland", "Alice in Wonderland", "2006-10-21","",'N',0);
        
        libraryService.setBook(newbook);
        Thread.sleep(3000);

        User user = new User(345, "Alex", "Crossing Street", borrowList);

        ObjectMapper objectMapper=new ObjectMapper();

        String userString = objectMapper.writeValueAsString(user);
        String uri = "/books/return/{bookid}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
            .put(uri, newbook.getId())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(userString)
        )
        .andReturn();

        Assertions.assertEquals(200,mvcResult.getResponse().getStatus());

        Thread.sleep(3000);
        MvcResult mvcResult2 = mvc.perform(MockMvcRequestBuilders.get("/books")
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        Assertions.assertEquals(200, mvcResult2.getResponse().getStatus());
        String booksJson = mvcResult2.getResponse().getContentAsString();

        List<Book> bookList = objectMapper.readValue(booksJson,new TypeReference<List<Book>>(){});

        var bookMatched = bookList.stream().filter(book->book.getId()==book.getId()).collect(Collectors.toList());
        
        Assertions.assertEquals('Y', bookMatched.get(0).getIsAvailable());
    }

    @Test
    void shouldRemoveBookFromBorrowList() throws Exception{
        var borrowList = new ArrayList<Integer>();
        borrowList.add(44234);
        User user = new User(345, "Alex", "Crossing Street", borrowList);
        ObjectMapper objectMapper=new ObjectMapper();
        String userString = objectMapper.writeValueAsString(user);
        String uri = "/books/return/{bookid}";
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
        Assertions.assertFalse(resultUser.getBorrowed().contains(44234));
    }

}
