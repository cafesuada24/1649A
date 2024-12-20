package com.hahsm.book.model;

public class Book implements Comparable<Book> {
    private int id;
    private int year;
    private String title;
    private String author;

    public Book() {
    }

    public Book(String title, String author, int year) {
        setTitle(title);
        setAuthor(author);
        setYear(year);
    }

    public Book(int id, String title, String author, int year) {
        this(title, author, year);
        this.setID(id);

    }

    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public int compareTo(Book o) {
        if (getTitle() != o.getTitle()) {
            return getTitle().compareTo(o.getTitle());
        }

        if (getAuthor() != o.getAuthor()) {
            return getAuthor().compareTo(o.getAuthor());
        }
        return getYear() == o.getYear() ? 0 : (getYear() < o.getYear() ? -1 : 1);
    }

    @Override
    public String toString() {
        return "ID: " + this.getID() + ", title: " + this.getTitle() + ", author: " + this.getAuthor() + " ("
                + this.getYear() + ")";
    }
}
