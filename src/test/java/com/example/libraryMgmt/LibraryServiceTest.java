package com.example.libraryMgmt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.librarymgmt.model.User;
import com.example.librarymgmt.service.LibraryService;

@SpringBootTest
public class LibraryServiceTest {
    
    @Autowired
    LibraryService libraryService;

    @Test
    public void shouldReturnNull(){
        var books=libraryService.fetchBooks();

        Assertions.assertNull(books);
    }

    @Test
    public void shouldReturnBooks(){
        var books=libraryService.fetchBooks();

        Assertions.assertTrue(books.size()>0);
    }

    @Test
    public void shouldAddBookToBorrowList(){
        User user = new User(345, "Alex", "Crossing Street", null);

        String response= libraryService.addBooks(user, 44234);
        Assertions.assertEquals(response, "Books added to borrowlist");
    }
}
