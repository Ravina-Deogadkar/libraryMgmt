package com.example.librarymgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.librarymgmt.model.Book;
import com.example.librarymgmt.service.LibraryService;

@RestController
public class LibraryController {
    
    @Autowired
    LibraryService libraryService;


    @RequestMapping("/hello")
    @ResponseBody
    public String helloString(){
        return "Hello";
    }

    @RequestMapping("/books")
    @ResponseBody
    public List<Book> fetch(){
        return libraryService.fetchBooks();
    }
}
