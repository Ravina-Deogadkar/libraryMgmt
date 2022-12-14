package com.example.librarymgmt.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
    value = HttpStatus.BAD_REQUEST, 
    reason = "Borrow list already have two books"
)
public class BorrowLimitException 
        extends RuntimeException {
 
    public BorrowLimitException(String string) {
        super(string);
    }
}