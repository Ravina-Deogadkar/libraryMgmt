package com.example.librarymgmt.model;

import java.util.List;

public class User {
    private Integer id;
    private String name;
    private String address;
    private List<Integer> borrowed;
    public User(Integer id, String name, String address, List<Integer> borrowed) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.borrowed = borrowed;
    }
    public User() {
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public List<Integer> getBorrowed() {
        return borrowed;
    }
    public void setBorrowed(List<Integer> borrowed) {
        this.borrowed = borrowed;
    }
    
}
