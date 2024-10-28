package com.hahsm.book.model;

public class Book {
    private int id;
    private String title;
    private String author;

    public Book() {}

    public Book(int id, String title, String author) {
        this.setID(id);
        this.setTitle(title);
        this.setAuthor(author);
    }

    public int getID() {
        return this.id;     
    }
    public void setID(int id) {}

    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
