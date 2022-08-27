package com.example.librarymgmt.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class LibraryController {
    
    @RequestMapping("/hello")
    @ResponseBody
    public String helloString(){
        return "Hello";
    }
}
