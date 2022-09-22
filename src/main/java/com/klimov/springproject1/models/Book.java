package com.klimov.springproject1.models;


import javax.validation.constraints.*;

public class Book {

    private int book_id;

    @NotEmpty(message = "Book title cant be empty!")
    @Size(max = 200, message = "Title cant be longer than 200 characters!")
    private String title;

    @NotEmpty(message = "Author name cant be empty!")
    @Size(max = 100, message = "Author name length cant exceed 100 characters!")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+", message = "Author's name should be in format \"Name Surname\"")
    private String author;

    @Min(value = 0, message = "No books written before the year 0")
    @Max(value = 2022, message = "No books from the future!")
    @NotNull(message = "Year of publishing can't be empty!")
    private Integer publish_year;

    private Integer client_id;

    public Book() {

    }

    public Book(int book_id, String title, String author, int publish_year, Integer client_id) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.publish_year = publish_year;
        this.client_id = client_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublish_year() {
        return publish_year;
    }

    public void setPublish_year(int publish_year) {
        this.publish_year = publish_year;
    }

    public Integer getClient_id() {
        return client_id;
    }

    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
    }

    @Override
    public String toString() {
        return "Book{" +
                "book_id=" + book_id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publish_year=" + publish_year +
                ", client_id=" + client_id +
                '}';
    }
}
