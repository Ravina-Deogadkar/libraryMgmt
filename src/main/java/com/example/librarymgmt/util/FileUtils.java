package com.example.librarymgmt.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.librarymgmt.model.Book;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FileUtils {

    public static void writeBooksToFile(List<Book> books) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			File file = new File("src/main/resources/data/books.json");
			mapper.writeValue(file, books);

		} catch (StreamWriteException e) {
			e.printStackTrace();
		} catch (DatabindException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public static List<Book> readBooksFromFile() {
		ObjectMapper mapper = new ObjectMapper();
		List<Book> books = new ArrayList<>();
		TypeReference<List<Book>> typeReference = new TypeReference<List<Book>>() {
		};
		InputStream inputStream = TypeReference.class.getResourceAsStream("/data/books.json");
		try {
			books = mapper.readValue(inputStream, typeReference);
		} catch (IOException e) {
			System.out.println("Unable to fetch books: " + e.getMessage());
		}
		return books;
	}
}
