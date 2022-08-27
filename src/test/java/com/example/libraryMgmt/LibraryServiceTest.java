package com.example.libraryMgmt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

        Assertions.assertNotNull(books);
    }
}
