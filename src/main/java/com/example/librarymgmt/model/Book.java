package com.example.librarymgmt.model;

import java.time.LocalDate;

public class Book {
    public int id;

    public String title;

    public String subtitle;

    public LocalDate firstPublishDate;

    public String description;

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

    public LocalDate getFirstPublishDate() {
        return firstPublishDate;
    }

    public void setFirstPublishDate(LocalDate firstPublishDate) {
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

}
