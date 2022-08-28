package com.example.librarymgmt.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
    value = HttpStatus.BAD_REQUEST, 
    reason = "Requested student does not exist"
)
public class BorrowLimitException 
        extends RuntimeException {
 
    public BorrowLimitException(String string) {
        super(string);
    }
}