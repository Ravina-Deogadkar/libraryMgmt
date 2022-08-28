package com.example.librarymgmt.model;

import java.time.LocalDate;

public class Book {
    private int id;

    private String title;

    private String subtitle;

    private String firstPublishDate;

    private String description;

    private Character isAvailable;

    private int copyAvailable;

    public Book() {
    }

    public Book(int id, String title, String subtitle, String firstPublishDate, String description, Character isAvailable, int copyAvailable) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.firstPublishDate = firstPublishDate;
        this.description = description;
        this.isAvailable = isAvailable;
        this.copyAvailable = copyAvailable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getFirstPublishDate() {
        return firstPublishDate;
    }

    public void setFirstPublishDate(String firstPublishDate) {
        this.firstPublishDate = firstPublishDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getCopyAvailable() {
        return copyAvailable;
    }

    public void setCopyAvailable(int copyAvailable) {
        this.copyAvailable = copyAvailable;
    }

    public Character getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Character isAvailable) {
        this.isAvailable = isAvailable;
    }

}
