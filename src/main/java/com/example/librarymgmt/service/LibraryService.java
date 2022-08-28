package com.example.librarymgmt.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.librarymgmt.model.Book;
import com.example.librarymgmt.model.User;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LibraryService {
    


    public List<Book> fetchBooks(){

        List<Book> books=new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<Book>> typeReference = new TypeReference<List<Book>>(){};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/data/books.json");
			try {
				books = mapper.readValue(inputStream,typeReference).stream().filter(book -> book.getIsAvailable().equals('Y')).collect(Collectors.toList());
			} catch (IOException e){
				System.out.println("Unable to fetch books: " + e.getMessage());
			}

        return books;
    }

	public User addBooks(User user, int bookId){
		//assuming books are already available.
		List<Integer> borrowList = new ArrayList<>();
		if(user.getBorrowed()!=null){
			borrowList = user.getBorrowed();
		}
		borrowList.add(bookId);
		user.setBorrowed(borrowList);

		//set isVisble to false for the bookid
		//List<Book> books =fetchBooks();

		//copying this code to not delete data from json file
        ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<Book>> typeReference = new TypeReference<List<Book>>(){};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/data/books.json");
		List<Book> books = new ArrayList<>();
		try {
			books = mapper.readValue(inputStream,typeReference);
		
			for(Book book : books){
				if(book.getId()==bookId){
					book.setIsAvailable('N');
				}
			}
		}catch (IOException e){
			System.out.println("Unable to fetch books: " + e.getMessage());
		}

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			File file = new File("src/main/resources/data/books.json");
			FileOutputStream out = new FileOutputStream(file);
			objectMapper.writeValue(file, books);

		} catch (StreamWriteException e) {
			e.printStackTrace();
		} catch (DatabindException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return user;
	}

    
}
