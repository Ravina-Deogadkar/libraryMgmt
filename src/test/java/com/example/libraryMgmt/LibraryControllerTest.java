package com.example.libraryMgmt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.librarymgmt.controller.LibraryController;

@SpringBootTest
public class LibraryControllerTest {
    
    @Autowired
    LibraryController libraryController;

   
    @Test
    void shouldReturnHello(){
        Assertions.assertEquals("Hello", libraryController.helloString());
    }
}
