package com.example.libraryMgmt;

import java.util.ArrayList;
import java.util.List;
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
    public void shouldRemoveBookFromLibrary() throws InterruptedException{
        Book newbook = new Book(45556,"Alice in Wonderland", "Alice in Wonderland", "2006-10-21","",'Y',1);
        
        libraryService.setBook(newbook);
        Thread.sleep(3000);
        
        User user = new User(345, "Alex", "Crossing Street", null);
        User response= libraryService.addBooks(user,newbook.getId() );
        //Assertions.assertTrue(response.getBorrowed().size()>0);

        Thread.sleep(3000);
        
        List<Book> books=libraryService.fetchBooks();

        List<Book> bookMatched = books.stream().filter(book->book.getId()==newbook.getId()).collect(Collectors.toList());
        
        Assertions.assertEquals(bookMatched.size(),0);
    }

    @Test
    public void shouldReduceCopyAvailableCount() throws InterruptedException{
        User user = new User(345, "Alex", "Crossing Street", null);
        int bookId=43562;

        List<Book> books=libraryService.fetchBooks();

        List<Book> bookBefore = books.stream().filter(book->book.getId()==bookId).collect(Collectors.toList());

        User response= libraryService.addBooks(user, bookId);

        Thread.sleep(3000);
        List<Book> booksAfter=libraryService.fetchBooks();

        List<Book> bookAfter = booksAfter.stream().filter(book->book.getId()==bookId).collect(Collectors.toList());

        Assertions.assertTrue(bookBefore.get(0).getCopyAvailable()>bookAfter.get(0).getCopyAvailable());
    }

    @Test
    public void shouldIncreaseCopyAvailableCount() throws InterruptedException{
        var borrowList = new ArrayList<Integer>();
        borrowList.add(44234);
        borrowList.add(43562);
        
        User user = new User(345, "Alex", "Crossing Street", borrowList);
        int bookId=43562;

        List<Book> books=libraryService.fetchBooks();

        List<Book> bookBefore = books.stream().filter(book->book.getId()==bookId).collect(Collectors.toList());

        User response= libraryService.returnBooks(user, bookId);

        Thread.sleep(3000);
        List<Book> booksAfter=libraryService.fetchBooks();

        List<Book> bookAfter = booksAfter.stream().filter(book->book.getId()==bookId).collect(Collectors.toList());

        Assertions.assertTrue(bookBefore.get(0).getCopyAvailable()<bookAfter.get(0).getCopyAvailable());
    }


    @Test
    public void shouldAddUnAvailableBookToLibrary() throws InterruptedException{
        var borrowList = new ArrayList<Integer>();
        borrowList.add(44234);
        borrowList.add(45556);

        Book newbook = new Book(45556,"Alice in Wonderland", "Alice in Wonderland", "2006-10-21","",'N',0);
        
        libraryService.setBook(newbook);
        Thread.sleep(3000);
        User user = new User(345, "Alex", "Crossing Street", borrowList);

        User response= libraryService.returnBooks(user, newbook.getId());

        Thread.sleep(3000);
        List<Book> books=libraryService.fetchBooks();

        List<Book> bookMatched = books.stream().filter(book->book.getId()==newbook.getId()).collect(Collectors.toList());

        Assertions.assertEquals('Y',bookMatched.get(0).getIsAvailable());
    }
}
