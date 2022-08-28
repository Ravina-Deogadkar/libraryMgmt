package com.example.librarymgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.librarymgmt.exception.BorrowLimitException;
import com.example.librarymgmt.model.Book;
import com.example.librarymgmt.model.User;
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

    @RequestMapping(value = "/books", method=RequestMethod.GET)
    @ResponseBody
    public List<Book> fetch(){
        return libraryService.fetchBooks();
    }

    @RequestMapping(value = "/book/add/{bookid}", method=RequestMethod.PUT)
    @ResponseBody
    public User addBooks(@PathVariable("bookid") Integer bookId, @RequestBody User user){
        if(user.getBorrowed()!=null && user.getBorrowed().size()==2)
            throw new BorrowLimitException("Above Borrow Limit");
            
        return libraryService.addBooks(user, bookId);
    }
}
