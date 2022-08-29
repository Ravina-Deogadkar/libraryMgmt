package com.example.librarymgmt.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
    value = HttpStatus.BAD_REQUEST, 
    reason = "Book unavailable"
)
public class BookUnavailableException 
        extends RuntimeException {
 
    public BookUnavailableException(String string) {
        super(string);
    }
}