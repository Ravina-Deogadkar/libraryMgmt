package com.example.librarymgmt.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.librarymgmt.model.Book;
import com.example.librarymgmt.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LibraryService {
    


    public List<Book> fetchBooks(){

        List<Book> books=new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<Book>> typeReference = new TypeReference<List<Book>>(){};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/data/books.json");
			try {
				books = mapper.readValue(inputStream,typeReference);
			} catch (IOException e){
				System.out.println("Unable to fetch books: " + e.getMessage());
			}

        return books;
    }

	public User addBooks(User user, int bookId){
		List<Integer> borrowList = new ArrayList<>();
		if(user.getBorrowed()!=null){
			borrowList = user.getBorrowed();
		}
		borrowList.add(bookId);
		user.setBorrowed(borrowList);
		return user;
	}

    
}
