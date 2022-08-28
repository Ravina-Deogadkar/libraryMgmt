package com.example.libraryMgmt;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.librarymgmt.model.Book;
import com.example.librarymgmt.model.User;
import com.example.librarymgmt.service.LibraryService;

@SpringBootTest
public class LibraryServiceTest {
    
    @Autowired
    LibraryService libraryService;

    // @Test
    // public void shouldReturnNull(){
    //     var books=libraryService.fetchBooks();

    //     Assertions.assertNull(books);
    // }

    @Test
    public void shouldReturnBooks(){
        var books=libraryService.fetchBooks();

        Assertions.assertTrue(books.size()>0);
    }

    @Test
    public void shouldAddBookToBorrowList(){
        User user = new User(345, "Alex", "Crossing Street", null);

        User response= libraryService.addBooks(user, 44234);
        Assertions.assertTrue(response.getBorrowed().size()>0);
    }


    @Test
    public void shouldRemoveBookFromLibrary(){
        User user = new User(345, "Alex", "Crossing Street", null);
        int bookId=44234;
        User response= libraryService.addBooks(user,bookId );
        //Assertions.assertTrue(response.getBorrowed().size()>0);

        List<Book> books=libraryService.fetchBooks();

        List<Book> bookMatched = books.stream().filter(book->book.getId()==bookId).collect(Collectors.toList());
        
        Assertions.assertNull(bookMatched);
    }
}
