package com.example.librarymgmt.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.librarymgmt.exception.BookUnavailableException;
import com.example.librarymgmt.exception.BorrowLimitException;
import com.example.librarymgmt.exception.DuplicateCopyBorrowException;
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
        
        if(user.getBorrowed()!=null && user.getBorrowed().contains(bookId))
            throw new DuplicateCopyBorrowException("Copy already exist in borrow list");
        List<Book> books =fetch();
        var bookRequested= books.stream().filter(book->book.getId()==bookId).collect(Collectors.toList());
        
        if(bookRequested.size()==0)
            throw new BookUnavailableException("Book not available in library");


        return libraryService.addBooks(user, bookId);
    }

    @RequestMapping(value = "/book/return/{bookid}", method=RequestMethod.PUT)
    @ResponseBody
    public User returnBooks(@PathVariable("bookid") Integer bookId, @RequestBody User user){

        return user;
    }
}
