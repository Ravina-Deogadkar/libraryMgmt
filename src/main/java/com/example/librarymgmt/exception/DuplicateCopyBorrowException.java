package com.example.librarymgmt.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
    value = HttpStatus.BAD_REQUEST, 
    reason = "Copy already exist in borrow list"
)
public class DuplicateCopyBorrowException 
        extends RuntimeException {
 
    public DuplicateCopyBorrowException(String string) {
        super(string);
    }
}