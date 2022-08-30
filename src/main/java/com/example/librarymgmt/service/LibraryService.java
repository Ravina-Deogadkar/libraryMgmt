package com.example.librarymgmt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.librarymgmt.model.Book;
import com.example.librarymgmt.model.User;
import com.example.librarymgmt.util.FileUtils;

@Service
public class LibraryService {

	public List<Book> fetchBooks() {

		List<Book> books = new ArrayList<>();
		var response = FileUtils.readBooksFromFile();
		books = response.stream().filter(book -> book.getIsAvailable().equals('Y')).collect(Collectors.toList());

		return books;
	}

	public User addBooks(User user, int bookId) {
		// assuming books are already available.
		List<Integer> borrowList = new ArrayList<>();
		if (user.getBorrowed() != null) {
			borrowList = user.getBorrowed();
		}
		borrowList.add(bookId);
		user.setBorrowed(borrowList);

		// set isVisble to false for the bookid
		List<Book> books = FileUtils.readBooksFromFile();
		for (Book book : books) {
			if (book.getId() == bookId) {
				if (book.getCopyAvailable() == 1)
					book.setIsAvailable('N');
				book.setCopyAvailable(book.getCopyAvailable() - 1);
			}
		}

		FileUtils.writeBooksToFile(books);
		return user;
	}

	public User returnBooks(User user, int bookId) {

		List<Book> books = FileUtils.readBooksFromFile();
		for (Book book : books) {
			if (book.getId() == bookId) {
				if (book.getCopyAvailable() == 0)
					book.setIsAvailable('Y');
				book.setCopyAvailable(book.getCopyAvailable() + 1);
			}
		}
		FileUtils.writeBooksToFile(books);
		
		return user;
	}

	// Only for testing
	public String setBook(Book book) {
		List<Book> books = FileUtils.readBooksFromFile();
		for(int i=0; i<books.size();i++){
			if(books.get(i).getId() == book.getId()){
				books.remove(i);
			}
		}
		books.add(book);

		FileUtils.writeBooksToFile(books);

		return "Successful";
	}

}
